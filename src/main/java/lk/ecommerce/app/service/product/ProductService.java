package lk.ecommerce.app.service.product;

import lk.ecommerce.app.dto.ProductDto;
import lk.ecommerce.app.entity.Product;

import java.util.List;

public interface ProductService {

    Product postProduct(ProductDto productDto);

    Product updateProduct(Long id, ProductDto productDto);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    void deleteProduct(Long id);
}
