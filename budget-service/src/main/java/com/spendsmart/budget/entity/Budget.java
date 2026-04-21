package com.spendsmart.budget.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private Double limitAmount;

    private String userEmail;

    public Budget() {}

    public Budget(Long id, String category, Double limitAmount, String userEmail) {
        this.id = id;
        this.category = category;
        this.limitAmount = limitAmount;
        this.userEmail = userEmail;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getLimitAmount() { return limitAmount; }
    public void setLimitAmount(Double limitAmount) { this.limitAmount = limitAmount; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}