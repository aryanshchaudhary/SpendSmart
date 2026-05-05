package com.spendsmart.income.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.income.dto.IncomeRequest;
import com.spendsmart.income.entity.Income;
import com.spendsmart.income.service.IncomeService;

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

@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc(addFilters = false)
class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncomeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddIncome() throws Exception {

        IncomeRequest request =
                new IncomeRequest("Salary", 5000.0, "Monthly");

        Income income =
                new Income(1L, "Salary", 5000.0, "Monthly", email);

        when(service.addIncome(any(), any())).thenReturn(income);

        mockMvc.perform(post("/api/incomes")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("Salary"));
    }

    // ✅ GET USER INCOMES
    @Test
    void testGetIncomes() throws Exception {

        when(service.getIncomes(email))
                .thenReturn(List.of(
                        new Income(1L, "Salary", 5000.0, "Monthly", email)
                ));

        mockMvc.perform(get("/api/incomes")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].source").value("Salary"));
    }

    // ✅ DELETE
    @Test
    void testDeleteIncome() throws Exception {

        mockMvc.perform(delete("/api/incomes/1")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Income deleted successfully"));
    }
}