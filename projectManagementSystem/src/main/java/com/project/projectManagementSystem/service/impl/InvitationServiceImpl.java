package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Invitation;
import com.project.projectManagementSystem.repository.InvitationRepository;
import com.project.projectManagementSystem.service.EmailService;
import com.project.projectManagementSystem.service.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();

        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new BadCredentialsException("Invalid token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String username) {
        Invitation invitation = invitationRepository.findByEmail(username);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        invitationRepository.delete(invitationRepository.findByToken(token));
    }
}
