package com.spendsmart.income.service;

import com.spendsmart.income.dto.IncomeRequest;
import com.spendsmart.income.entity.Income;
import com.spendsmart.income.exception.ResourceNotFoundException;
import com.spendsmart.income.repository.IncomeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class IncomeServiceTest {

    @Mock
    private IncomeRepository repository;

    @Mock
    private IncomeProducer producer;

    @InjectMocks
    private IncomeService service;

    private final String email = "test@mail.com";

    // ✅ CREATE (WITH EVENT)
    @Test
    void testAddIncome() {

        IncomeRequest request =
                new IncomeRequest("Salary", 5000.0, "Monthly");

        Income saved =
                new Income(1L, "Salary", 5000.0, "Monthly", email);

        when(repository.save(any())).thenReturn(saved);

        Income result = service.addIncome(request, email);

        assertNotNull(result);
        assertEquals("Salary", result.getSource());

        verify(repository, times(1)).save(any());
        verify(producer, times(1)).sendIncomeEvent(any()); // 🔥 important
    }

    // ✅ GET USER INCOMES
    @Test
    void testGetIncomes() {

        when(repository.findByUserEmail(email))
                .thenReturn(List.of(
                        new Income(1L, "Salary", 5000.0, "Monthly", email)
                ));

        List<Income> result = service.getIncomes(email);

        assertEquals(1, result.size());
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdateIncome() {

        Income existing =
                new Income(1L, "Old", 1000.0, "Old desc", email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        IncomeRequest request =
                new IncomeRequest("New", 2000.0, "New desc");

        Income updated = service.updateIncome(1L, request, email);

        assertEquals("New", updated.getSource());
        assertEquals(2000.0, updated.getAmount());
    }

    // ❌ UPDATE NOT FOUND
    @Test
    void testUpdateIncome_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        IncomeRequest request =
                new IncomeRequest("New", 2000.0, "New desc");

        assertThrows(ResourceNotFoundException.class, () -> {
            service.updateIncome(1L, request, email);
        });
    }

    // ✅ DELETE SUCCESS
    @Test
    void testDeleteIncome() {

        Income income =
                new Income(1L, "Salary", 5000.0, "Monthly", email);

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.of(income));

        service.deleteIncome(1L, email);

        verify(repository, times(1)).delete(income);
    }

    // ❌ DELETE NOT FOUND
    @Test
    void testDeleteIncome_NotFound() {

        when(repository.findByIdAndUserEmail(1L, email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteIncome(1L, email);
        });
    }
}