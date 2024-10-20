package lk.ecommerce.app.service.category;

import jakarta.persistence.EntityNotFoundException;
import lk.ecommerce.app.dto.CategoryDto;
import lk.ecommerce.app.dto.ProductDto;
import lk.ecommerce.app.entity.Category;
import lk.ecommerce.app.entity.Product;
import lk.ecommerce.app.repository.CategoryRepository;
import lk.ecommerce.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Category saveOrUpdateCategory(Category category, CategoryDto categoryDto) {
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        Product product = productRepository.findById(categoryDto.getProduct_id()).orElseThrow(()-> new RuntimeException("Product not found"));
//        Long id = product.getId();

        category.setProduct(product);

        return categoryRepository.save(category);
    }

    @Override
    public Category postCategory(CategoryDto categoryDto) {
        return saveOrUpdateCategory(new Category(), categoryDto);
    }

    @Override
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return saveOrUpdateCategory(optionalCategory.get(), categoryDto);
        }else {
            throw new EntityNotFoundException("Category not found with this %s".formatted(categoryDto.getId()));
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        }else {
            throw new EntityNotFoundException("Category not found with this id");
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream().sorted(Comparator.comparing(Category::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            categoryRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Category not found with this id "+id);
        }
    }
}
