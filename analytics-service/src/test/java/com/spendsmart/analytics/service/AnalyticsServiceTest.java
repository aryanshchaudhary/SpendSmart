package com.spendsmart.analytics.service;

import com.spendsmart.analytics.client.ExpenseClient;
import com.spendsmart.analytics.client.IncomeClient;
import com.spendsmart.analytics.dto.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private ExpenseClient expenseClient;

    @Mock
    private IncomeClient incomeClient;

    @InjectMocks
    private AnalyticsService service;

    private final String email = "test@mail.com";

    // ✅ BASIC CALCULATION
    @Test
    void testGetAnalytics() {

        ExpenseDto e1 = new ExpenseDto();
        e1.setAmount(100.0);

        ExpenseDto e2 = new ExpenseDto();
        e2.setAmount(200.0);

        IncomeDto i1 = new IncomeDto();
        i1.setAmount(1000.0);

        when(expenseClient.getExpenses(email))
                .thenReturn(List.of(e1, e2));

        when(incomeClient.getIncomes(email))
                .thenReturn(List.of(i1));

        AnalyticsResponse response = service.getAnalytics(email);

        assertEquals(1000.0, response.getTotalIncome());
        assertEquals(300.0, response.getTotalExpense());
        assertEquals(700.0, response.getBalance());
    }

    // 🔥 EMPTY DATA
    @Test
    void testGetAnalytics_Empty() {

        when(expenseClient.getExpenses(email)).thenReturn(List.of());
        when(incomeClient.getIncomes(email)).thenReturn(List.of());

        AnalyticsResponse response = service.getAnalytics(email);

        assertEquals(0.0, response.getTotalIncome());
        assertEquals(0.0, response.getTotalExpense());
        assertEquals(0.0, response.getBalance());
    }

    // 🔥 NULL AMOUNTS (VERY IMPORTANT EDGE CASE)
    @Test
    void testGetAnalytics_WithNullAmounts() {

        ExpenseDto e1 = new ExpenseDto();
        e1.setAmount(null);

        IncomeDto i1 = new IncomeDto();
        i1.setAmount(null);

        when(expenseClient.getExpenses(email))
                .thenReturn(List.of(e1));

        when(incomeClient.getIncomes(email))
                .thenReturn(List.of(i1));

        AnalyticsResponse response = service.getAnalytics(email);

        assertEquals(0.0, response.getTotalIncome());
        assertEquals(0.0, response.getTotalExpense());
        assertEquals(0.0, response.getBalance());
    }
}