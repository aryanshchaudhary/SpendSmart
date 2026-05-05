package com.spendsmart.budget.consumer;

import com.spendsmart.budget.service.BudgetService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExpenseConsumer {

    private final BudgetService budgetService;

    public ExpenseConsumer(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @RabbitListener(queues = "expense_queue")
    public void consumeExpense(Map<String, Object> event) {

        String category = (String) event.get("category");
        Double amount = Double.valueOf(event.get("amount").toString());
        String email = (String) event.get("userEmail");

        System.out.println("📉 Expense received in Budget Service");

        budgetService.checkBudget(category, amount, email);
    }
}