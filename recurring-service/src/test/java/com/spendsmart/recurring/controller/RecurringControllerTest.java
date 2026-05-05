package com.spendsmart.recurring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.recurring.dto.RecurringRequest;
import com.spendsmart.recurring.entity.Recurring;
import com.spendsmart.recurring.service.RecurringService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecurringController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecurringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecurringService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test@mail.com";

    // ✅ CREATE
    @Test
    void testCreate() throws Exception {

        Recurring r = new Recurring();
        r.setTitle("Netflix");

        when(service.save(any())).thenReturn(r);

        mockMvc.perform(post("/api/recurring")
                .header("X-User-Email", email)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new RecurringRequest())))
                .andExpect(status().isOk());
    }

    // ✅ GET
    @Test
    void testGetAll() throws Exception {

        when(service.getAll(email)).thenReturn(List.of(new Recurring()));

        mockMvc.perform(get("/api/recurring")
                .header("X-User-Email", email))
                .andExpect(status().isOk());
    }

    // ✅ DELETE
    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/api/recurring/1")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Recurring deleted successfully"));
    }

    // 🔥 TRIGGER
    @Test
    void testTrigger() throws Exception {

        when(service.trigger(email))
                .thenReturn("Recurring transactions executed");

        mockMvc.perform(post("/api/recurring/trigger")
                .header("X-User-Email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Recurring transactions executed"));
    }
}