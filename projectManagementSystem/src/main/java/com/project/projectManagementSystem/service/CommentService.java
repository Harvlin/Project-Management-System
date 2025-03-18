package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Comments;

import java.util.List;

public interface CommentService {
    Comments createComment(Long issueId, Long userId, String content);
    void deleteComment(Long commentId, Long userId) throws Exception;
    List<Comments> findCommentsByIssueId(Long issueId);
}
