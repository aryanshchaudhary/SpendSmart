package com.spendsmart.recurring.dto;

public class TriggerResponse {

    private String message;

    public TriggerResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}