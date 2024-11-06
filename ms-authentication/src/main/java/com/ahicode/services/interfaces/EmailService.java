package com.ahicode.services.interfaces;

public interface EmailService {
    void sendConfirmationCode(String to, String confirmationCode);
}
