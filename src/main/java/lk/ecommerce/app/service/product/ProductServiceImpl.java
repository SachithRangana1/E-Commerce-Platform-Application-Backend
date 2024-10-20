package lk.ecommerce.app.service.product;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.ProductDto;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.entity.User;
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
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product saveOrUpdateProduct(Product product, ProductDto productDto){
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        User user = userRepository.findById(productDto.getUser_id()).orElseThrow(()-> new RuntimeException("User not found"));
//        Long user_id = user.getId();

        product.setUser(user);

        return productRepository.save(product);
    }
    @Override
    public Product postProduct(ProductDto productDto) {
        return saveOrUpdateProduct(new Product(), productDto);
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return saveOrUpdateProduct(optionalProduct.get(), productDto);
        }else {
            throw new EntityNotFoundException("Product not found with this %s".formatted(productDto.getId()));
        }
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }else {
            throw new EntityNotFoundException("Product not found with this id "+id);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Product not found with this id "+ id);
        }
    }
}
