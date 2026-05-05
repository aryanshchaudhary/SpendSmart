package com.spendsmart.notification.consumer;

import com.spendsmart.notification.dto.NotificationRequest;
import com.spendsmart.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExpenseConsumer {

    private final NotificationService notificationService;

    public ExpenseConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "expense_queue")
    public void consume(Map<String, Object> event) {

        String email = (String) event.get("userEmail");
        Double amount = Double.valueOf(event.get("amount").toString());
        String category = (String) event.get("category");

        NotificationRequest request = new NotificationRequest();
        request.setUserEmail(email);
        request.setMessage("💸 Expense Added: ₹" + amount + " for " + category);

        notificationService.sendNotification(request);
    }
}