package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.repository.IssueRepository;
import com.project.projectManagementSystem.service.IssueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue getIssueById(Long id) throws Exception {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) throws Exception {
        return List.of();
    }

    @Override
    public Issue createIssue(Issue issue, Long userId) throws Exception {
        return null;
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {

    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        return null;
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        return null;
    }
}
