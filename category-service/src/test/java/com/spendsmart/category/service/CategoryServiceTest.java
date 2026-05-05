package com.spendsmart.category.service;

import com.spendsmart.category.dto.CategoryRequest;
import com.spendsmart.category.entity.Category;
import com.spendsmart.category.exception.ResourceNotFoundException;
import com.spendsmart.category.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddCategory() {

        CategoryRequest request = new CategoryRequest("FOOD");

        Category saved = new Category(1L, "FOOD", email);

        when(repository.save(any())).thenReturn(saved);

        Category result = service.addCategory(request, email);

        assertNotNull(result);
        assertEquals("FOOD", result.getName());

        verify(repository, times(1)).save(any());
    }

    // ✅ GET
    @Test
    void testGetCategories() {

        when(repository.findByUserEmail(email))
                .thenReturn(List.of(new Category(1L, "FOOD", email)));

        List<Category> result = service.getCategories(email);

        assertEquals(1, result.size());
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdateCategory() {

        Category existing = new Category(1L, "OLD", email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        CategoryRequest request = new CategoryRequest("NEW");

        Category updated = service.updateCategory(1L, request, email);

        assertEquals("NEW", updated.getName());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateCategory_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        CategoryRequest request = new CategoryRequest("NEW");

        assertThrows(ResourceNotFoundException.class, () -> {
            service.updateCategory(1L, request, email);
        });
    }

    // ✅ DELETE SUCCESS
    @Test
    void testDeleteCategory() {

        Category category = new Category(1L, "FOOD", email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(category));

        service.deleteCategory(1L, email);

        verify(repository, times(1)).delete(category);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testDeleteCategory_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteCategory(1L, email);
        });
    }
}