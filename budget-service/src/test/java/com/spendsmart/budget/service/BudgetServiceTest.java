package com.spendsmart.budget.service;

import com.spendsmart.budget.dto.BudgetRequest;
import com.spendsmart.budget.entity.Budget;
import com.spendsmart.budget.exception.ResourceNotFoundException;
import com.spendsmart.budget.repository.BudgetRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private BudgetRepository repository;

    @InjectMocks
    private BudgetService service;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testAddBudget() {

        BudgetRequest request = new BudgetRequest("FOOD", 1000.0);
        Budget saved = new Budget(1L, "FOOD", 1000.0, email);

        when(repository.save(any())).thenReturn(saved);

        Budget result = service.addBudget(request, email);

        assertNotNull(result);
        assertEquals("FOOD", result.getCategory());

        verify(repository, times(1)).save(any());
    }

    // ✅ GET USER BUDGETS
    @Test
    void testGetBudgets() {

        when(repository.findByUserEmail(email))
                .thenReturn(List.of(new Budget(1L, "FOOD", 1000.0, email)));

        List<Budget> result = service.getBudgets(email);

        assertEquals(1, result.size());
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdateBudget() {

        Budget existing = new Budget(1L, "OLD", 500.0, email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        BudgetRequest request = new BudgetRequest("NEW", 1000.0);

        Budget updated = service.updateBudget(1L, request, email);

        assertEquals("NEW", updated.getCategory());
        assertEquals(1000.0, updated.getLimitAmount());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateBudget_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        BudgetRequest request = new BudgetRequest("NEW", 1000.0);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.updateBudget(1L, request, email);
        });
    }

    // ✅ DELETE SUCCESS
    @Test
    void testDeleteBudget() {

        Budget budget = new Budget(1L, "FOOD", 1000.0, email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(budget));

        service.deleteBudget(1L, email);

        verify(repository, times(1)).delete(budget);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testDeleteBudget_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteBudget(1L, email);
        });
    }

    // 🔥 CHECK BUDGET (logic test)
    @Test
    void testCheckBudget() {

        List<Budget> budgets = List.of(
                new Budget(1L, "FOOD", 1000.0, email)
        );

        when(repository.findByUserEmail(email)).thenReturn(budgets);

        // No exception → logic executed
        assertDoesNotThrow(() -> {
            service.checkBudget("FOOD", 1500.0, email); // exceeds
        });
    }
}