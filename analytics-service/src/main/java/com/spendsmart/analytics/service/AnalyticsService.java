package com.spendsmart.analytics.service;

import com.spendsmart.analytics.client.ExpenseClient;
import com.spendsmart.analytics.client.IncomeClient;
import com.spendsmart.analytics.dto.AnalyticsResponse;
import com.spendsmart.analytics.dto.ExpenseDto;
import com.spendsmart.analytics.dto.IncomeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final ExpenseClient expenseClient;
    private final IncomeClient incomeClient;

    public AnalyticsService(ExpenseClient expenseClient, IncomeClient incomeClient) {
        this.expenseClient = expenseClient;
        this.incomeClient = incomeClient;
    }

    public AnalyticsResponse getAnalytics(String email) {

        // 🔥 Call other services with email
        List<ExpenseDto> expenses = expenseClient.getExpenses(email);
        List<IncomeDto> incomes = incomeClient.getIncomes(email);

        double totalExpense = expenses.stream()
                .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0)
                .sum();

        double totalIncome = incomes.stream()
                .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                .sum();

        double balance = totalIncome - totalExpense;

        return new AnalyticsResponse(totalIncome, totalExpense, balance);
    }
}