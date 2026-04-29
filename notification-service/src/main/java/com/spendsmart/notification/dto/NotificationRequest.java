package com.spendsmart.notification.dto;

public class NotificationRequest {

    private String message;
    private String userEmail;

    public NotificationRequest() {}

    public NotificationRequest(String message, String userEmail) {
        this.message = message;
        this.userEmail = userEmail;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}