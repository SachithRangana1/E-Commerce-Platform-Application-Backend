package lk.ecommerce.app.service.cart;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.CartDto;
import lk.ecommerce.app.entity.Cart;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.entity.User;
import lk.ecommerce.app.repository.CartRepository;
import lk.ecommerce.app.repository.ProductRepository;
import lk.ecommerce.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart saveOrUpdateCart(Cart cart, CartDto cartDto){
        cart.setId(cartDto.getId());

        User user = userRepository.findById(cartDto.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
        cart.setUser(user);

        List<Product> products = productRepository.findAllById(cartDto.getProduct_ids());
        cart.setProductList(products);

        return cartRepository.save(cart);
    }
    @Override
    public Cart postCart(CartDto cartDto) {
        return saveOrUpdateCart(new Cart(), cartDto);
    }

    @Override
    public Cart updateCart(Long user_id, Long product_id, CartDto cartDto) {
        Optional<Cart> optionalCart = cartRepository.findCartById(user_id, product_id);
        if (optionalCart.isPresent()){
            return saveOrUpdateCart(optionalCart.get(), cartDto);
        }else {
            throw new EntityNotFoundException("Cart not fond with this id");
        }
    }

    @Override
    public Cart getCartById(Long user_id, Long product_id) {
        Optional<Cart> optionalCart = cartRepository.findCartById(user_id, product_id);
        if (optionalCart.isPresent()){
            return optionalCart.get();
        }else {
            throw new EntityNotFoundException("Cart not found");
        }
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll().stream().sorted(Comparator.comparing(cart -> cart.getUser().getId())).collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Long user_id, Long product_id) {
        Optional<Cart> optionalCart = cartRepository.findCartById(user_id, product_id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            for (Product product : cart.getProductList()) {
                product.setCart(null);
            }
            cart.getProductList().clear();
            cartRepository.save(cart);
            cartRepository.delete(cart);
        } else {
            throw new EntityNotFoundException("Cart not found");
        }
    }
}
