package com.spendsmart.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.category.dto.CategoryRequest;
import com.spendsmart.category.entity.Category;
import com.spendsmart.category.service.CategoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddCategory() throws Exception {

        CategoryRequest request = new CategoryRequest("FOOD");
        Category category = new Category(1L, "FOOD", email);

        when(service.addCategory(any(), any())).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FOOD"));
    }

    // ✅ GET
    @Test
    void testGetCategories() throws Exception {

        when(service.getCategories(email))
                .thenReturn(List.of(new Category(1L, "FOOD", email)));

        mockMvc.perform(get("/api/categories")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("FOOD"));
    }

    // ✅ UPDATE
    @Test
    void testUpdateCategory() throws Exception {

        Category updated = new Category(1L, "NEW", email);

        when(service.updateCategory(any(), any(), any())).thenReturn(updated);

        mockMvc.perform(put("/api/categories/1")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new CategoryRequest("NEW"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NEW"));
    }

    // ✅ DELETE
    @Test
    void testDeleteCategory() throws Exception {

        mockMvc.perform(delete("/api/categories/1")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
    }
}