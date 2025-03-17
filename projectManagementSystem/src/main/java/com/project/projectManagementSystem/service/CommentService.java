package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Comments;

import java.util.List;

public interface CommentService {
    Comments crateComment(Long issueId, Long userId, String content);
    void deleteComment(Long commentId, Long userId);
    List<Comments> findCommentsByIssueId(Long issueId);
}
