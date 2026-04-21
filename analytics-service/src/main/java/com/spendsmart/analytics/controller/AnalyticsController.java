package com.spendsmart.analytics.controller;

import com.spendsmart.analytics.dto.AnalyticsResponse;
import com.spendsmart.analytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public AnalyticsResponse getAnalytics(
            @RequestHeader("X-User-Email") String email
    ) {
        return service.getAnalytics(email);
    }
}