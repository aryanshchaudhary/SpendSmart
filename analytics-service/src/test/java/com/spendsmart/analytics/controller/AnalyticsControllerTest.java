package com.spendsmart.analytics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.analytics.dto.AnalyticsResponse;
import com.spendsmart.analytics.service.AnalyticsService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalyticsController.class)
@AutoConfigureMockMvc(addFilters = false)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService service;

    private final String email = "test@mail.com";

    @Test
    void testGetAnalytics() throws Exception {

        AnalyticsResponse response =
                new AnalyticsResponse(1000.0, 300.0, 700.0);

        when(service.getAnalytics(email)).thenReturn(response);

        mockMvc.perform(get("/api/analytics")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").value(1000.0))
                .andExpect(jsonPath("$.totalExpense").value(300.0))
                .andExpect(jsonPath("$.balance").value(700.0));
    }
}