package com.spendsmart.recurring.service;

import com.spendsmart.recurring.client.ExpenseClient;
import com.spendsmart.recurring.client.IncomeClient;
import com.spendsmart.recurring.dto.RecurringRequest;
import com.spendsmart.recurring.entity.Recurring;
import com.spendsmart.recurring.repository.RecurringRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RecurringServiceTest {

    @Mock
    private RecurringRepository repo;

    @Mock
    private ExpenseClient expenseClient;

    @Mock
    private IncomeClient incomeClient;

    @InjectMocks
    private RecurringService service;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testSave() {
        Recurring r = new Recurring();
        r.setTitle("Netflix");

        when(repo.save(any())).thenReturn(r);

        Recurring result = service.save(r);

        assertNotNull(result);
        verify(repo).save(r);
    }

    // ✅ GET
    @Test
    void testGetAll() {
        when(repo.findByUserEmail(email))
                .thenReturn(List.of(new Recurring()));

        List<Recurring> list = service.getAll(email);

        assertEquals(1, list.size());
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdate() {

        Recurring existing = new Recurring();
        existing.setUserEmail(email);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(existing);

        RecurringRequest request = new RecurringRequest();
        request.setTitle("Updated");

        Recurring updated = service.update(1L, request, email);

        assertEquals("Updated", updated.getTitle());
    }

    // ❌ UPDATE UNAUTHORIZED
    @Test
    void testUpdate_Unauthorized() {

        Recurring existing = new Recurring();
        existing.setUserEmail("other@mail.com");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        RecurringRequest request = new RecurringRequest();

        assertThrows(RuntimeException.class, () -> {
            service.update(1L, request, email);
        });
    }

    // ❌ DELETE UNAUTHORIZED
    @Test
    void testDelete_Unauthorized() {

        Recurring existing = new Recurring();
        existing.setUserEmail("other@mail.com");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(RuntimeException.class, () -> {
            service.delete(1L, email);
        });
    }

    // 🔥 TRIGGER EXPENSE
    @Test
    void testTrigger_Expense() {

        Recurring r = new Recurring();
        r.setType("EXPENSE");
        r.setTitle("Food");
        r.setAmount(100.0);
        r.setCategory("FOOD");
        r.setUserEmail(email);

        when(repo.findByUserEmail(email)).thenReturn(List.of(r));

        String result = service.trigger(email);

        assertEquals("Recurring transactions executed", result);

        verify(expenseClient, times(1))
                .createExpense(any(), eq(email));
    }

    // 🔥 TRIGGER INCOME
    @Test
    void testTrigger_Income() {

        Recurring r = new Recurring();
        r.setType("INCOME");
        r.setTitle("Salary");
        r.setAmount(1000.0);
        r.setUserEmail(email);

        when(repo.findByUserEmail(email)).thenReturn(List.of(r));

        service.trigger(email);

        verify(incomeClient, times(1))
                .createIncome(any(), eq(email));
    }
}