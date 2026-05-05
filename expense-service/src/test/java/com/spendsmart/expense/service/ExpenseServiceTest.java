package com.spendsmart.expense.service;

import com.spendsmart.expense.dto.ExpenseRequest;
import com.spendsmart.expense.entity.Expense;
import com.spendsmart.expense.exception.ResourceNotFoundException;
import com.spendsmart.expense.repository.ExpenseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @Mock
    private ExpenseProducer producer;

    @InjectMocks
    private ExpenseService service;

    // ✅ CREATE
    @Test
    void testAddExpense() {
        ExpenseRequest request = new ExpenseRequest("Food", 500.0, "FOOD");

        Expense saved = new Expense(1L, "Food", 500.0, "FOOD", "test@mail.com");

        when(repository.save(any())).thenReturn(saved);

        Expense result = service.addExpense(request, "test@mail.com");

        assertNotNull(result);
        assertEquals("Food", result.getTitle());

        verify(repository, times(1)).save(any());
        verify(producer, times(1)).sendEvent(any()); // 🔥 important
    }

    // ✅ GET USER EXPENSES
    @Test
    void testGetExpenses() {
        List<Expense> list = List.of(
                new Expense(1L, "Food", 500.0, "FOOD", "test@mail.com")
        );

        when(repository.findByUserEmail("test@mail.com")).thenReturn(list);

        List<Expense> result = service.getExpenses("test@mail.com");

        assertEquals(1, result.size());
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdateExpense() {
        Expense existing = new Expense(1L, "Old", 100.0, "OLD", "test@mail.com");

        when(repository.findByIdAndUserEmail(1L, "test@mail.com"))
                .thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        ExpenseRequest request = new ExpenseRequest("New", 200.0, "NEW");

        Expense updated = service.updateExpense(1L, request, "test@mail.com");

        assertEquals("New", updated.getTitle());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateExpense_NotFound() {
        when(repository.findByIdAndUserEmail(1L, "test@mail.com"))
                .thenReturn(Optional.empty());

        ExpenseRequest request = new ExpenseRequest("New", 200.0, "NEW");

        assertThrows(ResourceNotFoundException.class, () -> {
            service.updateExpense(1L, request, "test@mail.com");
        });
    }

    // ✅ DELETE SUCCESS
    @Test
    void testDeleteExpense() {
        Expense expense = new Expense(1L, "Food", 500.0, "FOOD", "test@mail.com");

        when(repository.findByIdAndUserEmail(1L, "test@mail.com"))
                .thenReturn(Optional.of(expense));

        service.deleteExpense(1L, "test@mail.com");

        verify(repository, times(1)).delete(expense);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testDeleteExpense_NotFound() {
        when(repository.findByIdAndUserEmail(1L, "test@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteExpense(1L, "test@mail.com");
        });
    }

    // ✅ SUMMARY
    @Test
    void testCategorySummary() {
        List<Expense> expenses = List.of(
                new Expense(1L, "Food", 100.0, "FOOD", "test@mail.com"),
                new Expense(2L, "Food2", 200.0, "FOOD", "test@mail.com"),
                new Expense(3L, "Travel", 300.0, "TRAVEL", "test@mail.com")
        );

        when(repository.findByUserEmail("test@mail.com")).thenReturn(expenses);

        Map<String, Double> summary = service.getCategorySummary("test@mail.com");

        assertEquals(300.0, summary.get("FOOD"));
        assertEquals(300.0, summary.get("TRAVEL"));
    }
}