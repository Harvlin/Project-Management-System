package com.project.projectManagementSystem.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailWithToken(String username, String link) throws MessagingException;
}
