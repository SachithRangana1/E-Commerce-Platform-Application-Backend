package lk.ecommerce.app.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.CategoryDto;
import lk.ecommerce.app.entity.Category;
import lk.ecommerce.app.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody CategoryDto categoryDto){
        Category createdCategory = categoryService.postCategory(categoryDto);
        if (createdCategory != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (EntityNotFoundException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Category not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Something went wrong"));
        }
    }
}
