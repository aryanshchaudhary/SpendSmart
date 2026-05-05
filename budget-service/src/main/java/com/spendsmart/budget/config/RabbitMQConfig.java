package com.spendsmart.budget.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXPENSE_QUEUE = "expense_queue";
    public static final String INCOME_QUEUE = "income_queue";
}