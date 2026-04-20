package com.spendsmart.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/user")
    public String user(@RequestHeader("X-User-Email") String email) {
        return "Hello USER " + email;
    }

    @GetMapping("/api/admin")
    public String admin(@RequestHeader("X-User-Email") String email) {
        return "Hello ADMIN " + email;
    }
}