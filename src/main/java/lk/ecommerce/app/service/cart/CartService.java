package lk.ecommerce.app.service.cart;

import lk.ecommerce.app.dto.CartDto;
import lk.ecommerce.app.entity.Cart;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.entity.User;

import java.util.List;

public interface CartService {

    Cart postCart(CartDto cartDto);

    Cart updateCart(Long user_id, Long product_id,  CartDto cartDto);

    Cart getCartById(Long user_id, Long product_id);

    List<Cart> getAllCarts();

    void deleteCart(Long user_id, Long product_id);
}
