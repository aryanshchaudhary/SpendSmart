package com.spendsmart.income.dto;

public class IncomeRequest {

    private String source;
    private Double amount;
    private String description;

    public IncomeRequest() {}

    public IncomeRequest(String source, Double amount, String description) {
        this.source = source;
        this.amount = amount;
        this.description = description;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}