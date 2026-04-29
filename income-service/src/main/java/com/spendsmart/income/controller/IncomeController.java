package com.spendsmart.income.controller;

import com.spendsmart.income.dto.IncomeRequest;
import com.spendsmart.income.entity.Income;
import com.spendsmart.income.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin("*")
public class IncomeController {

    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Income addIncome(
            @RequestBody IncomeRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.addIncome(request, email);
    }

    // USER READ
    @GetMapping
    public List<Income> getIncomes(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getIncomes(email);
    }

    // ADMIN READ ALL
    @GetMapping("/admin")
    public List<Income> getAllIncomes() {
        return service.getAllIncomes();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Income updateIncome(
            @PathVariable Long id,
            @RequestBody IncomeRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        return service.updateIncome(id, request, email);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteIncome(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email
    ) {
        service.deleteIncome(id, email);
        return "Income deleted successfully";
    }
}