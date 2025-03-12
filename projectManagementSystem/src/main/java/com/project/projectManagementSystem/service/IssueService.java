package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long id) throws Exception;
    List<Issue> getIssuesByProjectId(Long projectId) throws Exception;
    Issue createIssue(Issue issue, Long userId) throws Exception;
    void deleteIssue(Long issueId, Long userId) throws Exception;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;
}
