package com.spendsmart.income.service;

import com.spendsmart.income.dto.IncomeRequest;
import com.spendsmart.income.entity.Income;
import com.spendsmart.income.exception.ResourceNotFoundException;
import com.spendsmart.income.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public Income addIncome(IncomeRequest request, String email) {

        Income income = new Income();
        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setUserEmail(email);

        return repository.save(income);
    }

    public List<Income> getIncomes(String email) {
        return repository.findByUserEmail(email);
    }

    public Income updateIncome(Long id, IncomeRequest request, String email) {

        Income income = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());

        return repository.save(income);
    }

    public void deleteIncome(Long id, String email) {

        Income income = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        repository.delete(income);
    }
}