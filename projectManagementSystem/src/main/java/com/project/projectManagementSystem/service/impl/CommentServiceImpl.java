package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Comments;
import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.CommentRepository;
import com.project.projectManagementSystem.repository.IssueRepository;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.CommentService;
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
    public Comments crateComment(Long issueId, Long userId, String content) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        Optional<User> user = userRepository.findById(userId);

        Comments comments = new Comments();
        comments.setIssue(issue.get());
        comments.setUser(user.get());
        comments.setCreatedDateTime(LocalDateTime.now());
        comments.setContent(content);

        issue.get().getComments().add(comments);
        return commentRepository.save(comments);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {

    }

    @Override
    public List<Comments> findCommentsByIssueId(Long issueId) {
        return List.of();
    }
}
