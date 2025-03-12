package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailWithToken(String username, String link) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String subject = "Join Project Team Invitation";
        String text = "Click The Link To Join The Project Team Invitation.";

        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(username);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new MessagingException("Failed to send email", e);
        }
    }
}
