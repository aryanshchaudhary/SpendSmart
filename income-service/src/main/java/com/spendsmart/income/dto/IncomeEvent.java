package com.spendsmart.income.dto;

public class IncomeEvent {

    private String source;
    private Double amount;
    private String description;
    private String userEmail;

    public IncomeEvent() {}

    public IncomeEvent(String source, Double amount, String description, String userEmail) {
        this.source = source;
        this.amount = amount;
        this.description = description;
        this.userEmail = userEmail;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}