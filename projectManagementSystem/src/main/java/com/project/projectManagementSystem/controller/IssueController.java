package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.domain.dto.IssueDto;
import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.domain.request.IssueRequest;
import com.project.projectManagementSystem.domain.response.AuthResponse;
import com.project.projectManagementSystem.mapper.IssueMapper;
import com.project.projectManagementSystem.service.IssueService;
import com.project.projectManagementSystem.service.UserService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;
    private final UserService userService;
    private final IssueMapper issueMapper;

    @Autowired
    public IssueController(IssueService issueService, UserService userService, IssueMapper issueMapper) {
        this.issueService = issueService;
        this.userService = userService;
        this.issueMapper = issueMapper;
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDto> getIssueById(@PathVariable("issueId") Long issueId) throws Exception {
        Issue issue = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issueMapper.toDto(issue));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IssueDto>> getIssuesByProjectId(@PathVariable("projectId") Long projectId) throws Exception {
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        List<IssueDto> issueDtos = issues.stream().map(issueMapper::toDto).toList();
        return ResponseEntity.ok(issueDtos);
    }

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String token) throws Exception {
        System.out.println("issue -> " + issueRequest);
        User userToken = userService.findUserProfileByJwt(token);
        Issue issue = issueService.createIssue(issueMapper.fromRequest(issueRequest), userToken.getId());
        return ResponseEntity.ok(issueMapper.toDto(issue));
    }

    @DeleteMapping("/{issueId")
    public ResponseEntity deleteIssue(@PathVariable("issueId") Long issueId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<IssueDto> addUserToIssue(@PathVariable("issueId") Long issueId, @PathVariable("userId") Long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issueMapper.toDto(issue));
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<IssueDto> updateIssueStatus(@PathVariable Long issueId, @PathVariable("status") String status) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issueMapper.toDto(issue));
    }
}
