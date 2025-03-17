package com.project.projectManagementSystem.repository;

import com.project.projectManagementSystem.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByIssueId(Long issueId);
}
