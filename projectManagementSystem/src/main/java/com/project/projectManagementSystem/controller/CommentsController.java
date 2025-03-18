package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.domain.dto.CommentsDto;
import com.project.projectManagementSystem.domain.entity.Comments;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.domain.request.CreateCommentRequest;
import com.project.projectManagementSystem.mapper.CommentMapper;
import com.project.projectManagementSystem.service.CommentService;
import com.project.projectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentService commentService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentsController(CommentService commentService, UserService userService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @PostMapping()
    public ResponseEntity<CommentsDto> createComment(@RequestBody CreateCommentRequest createCommentRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Comments createdComment = commentService.createComment(createCommentRequest.getIssueId(), user.getId(), createCommentRequest.getContent());
        return new ResponseEntity<>(commentMapper.toDto(createdComment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<CommentsDto>> getCommentByIssueId(@PathVariable Long issueId, @RequestHeader("Authorization") String jwt) throws Exception {
        List<Comments> comments = commentService.findCommentsByIssueId(issueId);
        List<CommentsDto> commentsDtos = comments.stream().map(commentMapper::toDto).toList();
        return new ResponseEntity<>(commentsDtos, HttpStatus.OK);
    }
}
