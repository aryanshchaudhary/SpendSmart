package com.spendsmart.expense.controller;

import com.spendsmart.expense.dto.ExpenseRequest;
import com.spendsmart.expense.entity.Expense;
import com.spendsmart.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Expense addExpense(
            @RequestBody ExpenseRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.addExpense(request, email);
    }

    // READ
    @GetMapping
    public List<Expense> getExpenses(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getExpenses(email);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.updateExpense(id, request, email);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email
    ) {
        service.deleteExpense(id, email);
        return "Expense deleted successfully";
    }
}