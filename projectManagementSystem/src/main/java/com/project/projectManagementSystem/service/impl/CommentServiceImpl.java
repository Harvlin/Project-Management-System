package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Comments;
import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.CommentRepository;
import com.project.projectManagementSystem.repository.IssueRepository;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comments createComment(Long issueId, Long userId, String content) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found with id " + issueId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Comments comment = Comments.builder()
                .issue(issue)
                .user(user)
                .content(content)
                .createdDateTime(LocalDateTime.now())
                .build();

        issue.getComments().add(comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + commentId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        if (comments.getUser().equals(user)) {
            commentRepository.delete(comments);
        }
    }

    @Override
    public List<Comments> findCommentsByIssueId(Long issueId) {
        return commentRepository.findCommentsByIssueId(issueId);
    }
}
