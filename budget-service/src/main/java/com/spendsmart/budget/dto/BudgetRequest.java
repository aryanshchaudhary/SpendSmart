package com.spendsmart.budget.dto;

public class BudgetRequest {

    private String category;
    private Double limitAmount;

    public BudgetRequest() {}

    public BudgetRequest(String category, Double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getLimitAmount() { return limitAmount; }
    public void setLimitAmount(Double limitAmount) { this.limitAmount = limitAmount; }
}