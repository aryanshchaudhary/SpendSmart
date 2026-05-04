package com.spendsmart.expense.dto;

public class ExpenseEvent {

    private String title;
    private Double amount;
    private String category;
    private String userEmail;

    public ExpenseEvent() {}

    public ExpenseEvent(String title, Double amount, String category, String userEmail) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.userEmail = userEmail;
    }

    public String getTitle() { return title; }
    public Double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getUserEmail() { return userEmail; }

    public void setTitle(String title) { this.title = title; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}