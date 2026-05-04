package com.spendsmart.income.service;

import com.spendsmart.income.config.RabbitMQConfig;
import com.spendsmart.income.dto.IncomeEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncomeProducer {

    private final RabbitTemplate rabbitTemplate;

    public IncomeProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendIncomeEvent(IncomeEvent event) {

        System.out.println("🔥 INCOME EVENT SENT");

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}