package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.IssueRepository;
import com.project.projectManagementSystem.service.IssueService;
import com.project.projectManagementSystem.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final UserService userService;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository, UserService userService) {
        this.issueRepository = issueRepository;
        this.userService = userService;
    }

    @Override
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found with id " + id));
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(Issue issue, Long userId) {
        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) {
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception{
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
