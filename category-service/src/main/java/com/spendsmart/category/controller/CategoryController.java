package com.spendsmart.category.controller;

import com.spendsmart.category.dto.CategoryRequest;
import com.spendsmart.category.entity.Category;
import com.spendsmart.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public Category addCategory(
            @RequestBody CategoryRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.addCategory(request, email);
    }

    @GetMapping
    public List<Category> getCategories(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getCategories(email);
    }

    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.updateCategory(id, request, email);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email
    ) {
        service.deleteCategory(id, email);
        return "Category deleted successfully";
    }
}