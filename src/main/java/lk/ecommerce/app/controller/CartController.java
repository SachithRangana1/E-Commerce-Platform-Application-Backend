package lk.ecommerce.app.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.CartDto;
import lk.ecommerce.app.entity.Cart;
import lk.ecommerce.app.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> postCart(@RequestBody CartDto cartDto){
        Cart createdcart = cartService.postCart(cartDto);
        if (createdcart != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdcart);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{user_id}/{product_id}")
    public ResponseEntity<?> updateCart(@PathVariable Long user_id, @PathVariable Long product_id, @RequestBody CartDto cartDto){
        try {
            return ResponseEntity.ok(cartService.updateCart(user_id, product_id, cartDto));
        } catch (EntityActionVetoException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/{user_id}/{product_id}")
    public ResponseEntity<?> getCartById(@PathVariable Long user_id, @PathVariable Long product_id){
        try {
            return ResponseEntity.ok(cartService.getCartById(user_id, product_id));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCarts(){
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @DeleteMapping("/{user_id}/{product_id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long user_id, @PathVariable Long product_id){
        try {
            cartService.deleteCart(user_id, product_id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Cart not found"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Something went wrong"));
        }
    }

}
