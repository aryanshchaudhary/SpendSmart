package com.spendsmart.notification.consumer;

import com.spendsmart.notification.dto.NotificationRequest;
import com.spendsmart.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IncomeConsumer {

    private final NotificationService notificationService;

    public IncomeConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "income_queue")
    public void consumeIncome(Map<String, Object> event) {

        String email = (String) event.get("userEmail");
        Double amount = Double.valueOf(event.get("amount").toString());
        String source = (String) event.get("source");

        NotificationRequest request = new NotificationRequest();
        request.setUserEmail(email);
        request.setMessage("💰 Income Added: ₹" + amount + " from " + source);

        notificationService.sendNotification(request);
    }
}