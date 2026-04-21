package com.spendsmart.category.service;

import com.spendsmart.category.dto.CategoryRequest;
import com.spendsmart.category.entity.Category;
import com.spendsmart.category.exception.ResourceNotFoundException;
import com.spendsmart.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category addCategory(CategoryRequest request, String email) {

        Category category = new Category();
        category.setName(request.getName());
        category.setUserEmail(email);

        return repository.save(category);
    }

    public List<Category> getCategories(String email) {
        return repository.findByUserEmail(email);
    }

    public Category updateCategory(Long id, CategoryRequest request, String email) {

        Category category = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(request.getName());

        return repository.save(category);
    }

    public void deleteCategory(Long id, String email) {

        Category category = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        repository.delete(category);
    }
}