package com.project.projectManagementSystem.repository;

import com.project.projectManagementSystem.domain.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Invitation findByToken(String token);
    Invitation findByEmail(String email);
}
