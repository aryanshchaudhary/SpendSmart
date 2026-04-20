package com.spendsmart.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/user")
    public String user() {
        return "Hello USER";
    }

    @GetMapping("/api/admin")
    public String admin() {
        return "Hello ADMIN";
    }
}