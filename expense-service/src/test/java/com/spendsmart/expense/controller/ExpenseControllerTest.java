package com.spendsmart.expense.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.expense.dto.ExpenseRequest;
import com.spendsmart.expense.entity.Expense;
import com.spendsmart.expense.service.ExpenseService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false) // 🔥 important
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddExpense() throws Exception {

        ExpenseRequest request = new ExpenseRequest("Food", 500.0, "FOOD");
        Expense expense = new Expense(1L, "Food", 500.0, "FOOD", email);

        when(service.addExpense(any(), any())).thenReturn(expense);

        mockMvc.perform(post("/api/expenses")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Food"));
    }

    // ✅ GET USER EXPENSES
    @Test
    void testGetExpenses() throws Exception {

        when(service.getExpenses(email))
                .thenReturn(List.of(new Expense(1L, "Food", 500.0, "FOOD", email)));

        mockMvc.perform(get("/api/expenses")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Food"));
    }

    // ✅ DELETE
    @Test
    void testDeleteExpense() throws Exception {

        mockMvc.perform(delete("/api/expenses/1")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense deleted successfully"));
    }

    // ✅ SUMMARY
    @Test
    void testSummary() throws Exception {

        when(service.getCategorySummary(email))
                .thenReturn(Map.of("FOOD", 300.0));

        mockMvc.perform(get("/api/expenses/summary")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.FOOD").value(300.0));
    }
}