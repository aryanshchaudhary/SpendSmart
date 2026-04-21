package com.spendsmart.notification.controller;

import com.spendsmart.notification.dto.NotificationRequest;
import com.spendsmart.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public String sendNotification(
            @RequestBody NotificationRequest request,
            @RequestHeader("X-User-Email") String email
    ) {
        request.setUserEmail(email);
        return service.sendNotification(request);
    }
}