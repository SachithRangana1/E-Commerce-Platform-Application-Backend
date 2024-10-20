package lk.ecommerce.app.service.category;

import lk.ecommerce.app.dto.CategoryDto;
import lk.ecommerce.app.entity.Category;

import java.util.List;

public interface CategoryService {

    Category postCategory(CategoryDto categoryDto);

    Category updateCategory(Long id, CategoryDto categoryDto);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    void deleteCategory(Long id);
}
