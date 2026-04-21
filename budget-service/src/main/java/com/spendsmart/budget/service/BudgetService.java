package com.spendsmart.budget.service;

import com.spendsmart.budget.dto.BudgetRequest;
import com.spendsmart.budget.entity.Budget;
import com.spendsmart.budget.exception.ResourceNotFoundException;
import com.spendsmart.budget.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository repository;

    public BudgetService(BudgetRepository repository) {
        this.repository = repository;
    }

    public Budget addBudget(BudgetRequest request, String email) {

        Budget budget = new Budget();
        budget.setCategory(request.getCategory());
        budget.setLimitAmount(request.getLimitAmount());
        budget.setUserEmail(email);

        return repository.save(budget);
    }

    public List<Budget> getBudgets(String email) {
        return repository.findByUserEmail(email);
    }

    public Budget updateBudget(Long id, BudgetRequest request, String email) {

        Budget budget = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        budget.setCategory(request.getCategory());
        budget.setLimitAmount(request.getLimitAmount());

        return repository.save(budget);
    }

    public void deleteBudget(Long id, String email) {

        Budget budget = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        repository.delete(budget);
    }
}