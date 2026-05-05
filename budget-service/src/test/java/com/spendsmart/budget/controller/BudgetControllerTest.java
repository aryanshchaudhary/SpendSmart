package com.spendsmart.budget.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.budget.dto.BudgetRequest;
import com.spendsmart.budget.entity.Budget;
import com.spendsmart.budget.service.BudgetService;

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

@WebMvcTest(BudgetController.class)
@AutoConfigureMockMvc(addFilters = false) // 🔥 important
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddBudget() throws Exception {

        BudgetRequest request = new BudgetRequest("FOOD", 1000.0);
        Budget budget = new Budget(1L, "FOOD", 1000.0, email);

        when(service.addBudget(any(), any())).thenReturn(budget);

        mockMvc.perform(post("/api/budgets")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("FOOD"));
    }

    // ✅ GET USER BUDGETS
    @Test
    void testGetBudgets() throws Exception {

        when(service.getBudgets(email))
                .thenReturn(List.of(new Budget(1L, "FOOD", 1000.0, email)));

        mockMvc.perform(get("/api/budgets")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("FOOD"));
    }

    // ✅ DELETE
    @Test
    void testDeleteBudget() throws Exception {

        mockMvc.perform(delete("/api/budgets/1")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Budget deleted successfully"));
    }
}