package com.spendsmart.recurring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "expense-service")
public interface ExpenseClient {

    @PostMapping("/api/expenses")
    Object createExpense(@RequestBody Map<String, Object> request,
                         @RequestHeader("X-User-Email") String email);
}