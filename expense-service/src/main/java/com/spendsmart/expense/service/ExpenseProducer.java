package com.spendsmart.expense.service;

import com.spendsmart.expense.config.RabbitMQConfig;
import com.spendsmart.expense.dto.ExpenseEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExpenseProducer {

    private final RabbitTemplate rabbitTemplate;

    public ExpenseProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(ExpenseEvent event) {
    	
    	System.out.println("🔥 PRODUCER CALLED");
    	
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}