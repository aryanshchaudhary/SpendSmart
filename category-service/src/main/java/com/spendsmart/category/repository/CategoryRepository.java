package com.spendsmart.category.repository;

import com.spendsmart.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserEmail(String userEmail);

    Optional<Category> findByIdAndUserEmail(Long id, String userEmail);
}