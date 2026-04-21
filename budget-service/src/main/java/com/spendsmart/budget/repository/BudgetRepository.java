package com.spendsmart.budget.repository;

import com.spendsmart.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserEmail(String userEmail);

    Optional<Budget> findByIdAndUserEmail(Long id, String userEmail);
}