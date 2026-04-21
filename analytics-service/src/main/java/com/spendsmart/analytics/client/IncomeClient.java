package com.spendsmart.analytics.client;

import com.spendsmart.analytics.dto.IncomeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "income-service")
public interface IncomeClient {

    @GetMapping("/api/incomes")
    List<IncomeDto> getIncomes(
            @RequestHeader("X-User-Email") String email
    );
}