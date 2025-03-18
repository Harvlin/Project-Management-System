package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long id) ;
    List<Issue> getIssuesByProjectId(Long projectId);
    Issue createIssue(Issue issue, Long userId);
    void deleteIssue(Long issueId, Long userId) ;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status);
}
