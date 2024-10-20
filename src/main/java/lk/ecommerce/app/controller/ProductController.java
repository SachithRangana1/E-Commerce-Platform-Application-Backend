package lk.ecommerce.app.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.ProductDto;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductDto productDto){
        Product createdProduct = productService.postProduct(productDto);
        if (createdProduct != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, ProductDto productDto){
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productDto));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(Collections.singletonMap("success", this));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Product not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Something went wrong"));
        }
    }
}
