package com.spendsmart.expense.controller;

import com.spendsmart.expense.dto.ExpenseRequest;
import com.spendsmart.expense.entity.Expense;
import com.spendsmart.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
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

    // USER READ 
    @GetMapping
    public List<Expense> getExpenses(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getExpenses(email);
    }

    // ADMIN READ ALL
    @GetMapping("/admin")
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
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

    // SUMMARY
    @GetMapping("/summary")
    public Map<String, Double> getCategorySummary(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getCategorySummary(email);
    }
}