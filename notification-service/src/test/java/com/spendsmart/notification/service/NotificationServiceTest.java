package com.spendsmart.notification.service;

import com.spendsmart.notification.dto.NotificationRequest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {

    private final NotificationService service = new NotificationService();

    // ✅ SUCCESS CASE
    @Test
    void testSendNotification() {

        NotificationRequest request =
                new NotificationRequest("Test message", "test@mail.com");

        String response = service.sendNotification(request);

        assertEquals("Notification sent successfully", response);
    }

    // 🔥 EDGE CASE (NULL MESSAGE)
    @Test
    void testSendNotification_NullMessage() {

        NotificationRequest request =
                new NotificationRequest(null, "test@mail.com");

        String response = service.sendNotification(request);

        assertEquals("Notification sent successfully", response);
    }

    // 🔥 EDGE CASE (NULL EMAIL)
    @Test
    void testSendNotification_NullEmail() {

        NotificationRequest request =
                new NotificationRequest("Hello", null);

        String response = service.sendNotification(request);

        assertEquals("Notification sent successfully", response);
    }
}