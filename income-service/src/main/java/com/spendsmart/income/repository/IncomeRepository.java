package com.spendsmart.income.repository;

import com.spendsmart.income.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserEmail(String userEmail);

    Optional<Income> findByIdAndUserEmail(Long id, String userEmail);
}