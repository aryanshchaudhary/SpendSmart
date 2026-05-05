package com.spendsmart.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.notification.dto.NotificationRequest;
import com.spendsmart.notification.service.NotificationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ SEND NOTIFICATION
    @Test
    void testSendNotification() throws Exception {

        NotificationRequest request = new NotificationRequest();
        request.setMessage("Hello");

        when(service.sendNotification(any()))
                .thenReturn("Notification sent successfully");

        mockMvc.perform(post("/api/notifications")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully"));
    }
}