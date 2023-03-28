package com.example.modeling_tools.service;

/**
 * Service used for sending emails.
 */
public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
