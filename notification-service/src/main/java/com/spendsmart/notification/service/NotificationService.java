package com.spendsmart.notification.service;

import com.spendsmart.notification.dto.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public String sendNotification(NotificationRequest request) {

        // Simulate notification (console log)
        System.out.println("🔔 Notification for " + request.getUserEmail());
        System.out.println("Message: " + request.getMessage());

        return "Notification sent successfully";
    }
}