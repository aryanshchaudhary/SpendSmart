package com.spendsmart.analytics.client;

import com.spendsmart.analytics.dto.ExpenseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "expense-service")
public interface ExpenseClient {

    @GetMapping("/api/expenses")
    List<ExpenseDto> getExpenses(
            @RequestHeader("X-User-Email") String email
    );
}