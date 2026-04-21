package com.spendsmart.recurring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "income-service")
public interface IncomeClient {

    @PostMapping("/api/incomes")
    Object createIncome(@RequestBody Map<String, Object> request,
                        @RequestHeader("X-User-Email") String email);
}