package lk.ecommerce.app.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.UserDto;
import lk.ecommerce.app.entity.User;
import lk.ecommerce.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserDto userDto){
        User createdUser = userService.postUser(userDto);
        if (createdUser != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        } catch (EntityActionVetoException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Something went wrong"));
        }
    }
}
