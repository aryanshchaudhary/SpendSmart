package com.spendsmart.budget.controller;

import com.spendsmart.budget.dto.BudgetRequest;
import com.spendsmart.budget.entity.Budget;
import com.spendsmart.budget.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin("*")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    // CREATE 
    @PostMapping
    public Budget addBudget(
            @RequestBody BudgetRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.addBudget(request, email);
    }

    // USER READ
    @GetMapping
    public List<Budget> getBudgets(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getBudgets(email);
    }

    // ADMIN READ ALL 
    @GetMapping("/admin")
    public List<Budget> getAllBudgets() {
        return service.getAllBudgets();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Budget updateBudget(
            @PathVariable Long id,
            @RequestBody BudgetRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.updateBudget(id, request, email);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteBudget(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email
    ) {
        service.deleteBudget(id, email);
        return "Budget deleted successfully";
    }
}