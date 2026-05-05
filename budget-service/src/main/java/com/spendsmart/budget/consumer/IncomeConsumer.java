package com.spendsmart.budget.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IncomeConsumer {

    @RabbitListener(queues = "income_queue")
    public void consumeIncome(Map<String, Object> event) {

        String email = (String) event.get("userEmail");
        Double amount = Double.valueOf(event.get("amount").toString());

        System.out.println("💰 Income received in Budget Service: ₹" + amount + " for " + email);
    }
}