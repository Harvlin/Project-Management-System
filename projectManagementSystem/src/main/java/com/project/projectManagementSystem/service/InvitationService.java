package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {
    void sendInvitation(String email, Long projectId) throws MessagingException;
    Invitation acceptInvitation(String token, Long userId);
    String getTokenByUserMail(String username);
    void deleteToken(String token);
}
