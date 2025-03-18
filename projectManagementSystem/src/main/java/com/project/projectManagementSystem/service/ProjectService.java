package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Chat;
import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user);
    List<Project> getProjectByTeam(User user, String category, String tag);
    Project getProjectById(Long id);
    void deleteProject(Long projectId, Long userId);
    Project updateProject(Long id, Project updatedProject);
    void addUserToProject(Long projectId, Long userId);
    void removeUserFromProject(Long projectId, Long userId);
    Chat getChatByProjectId(Long projectId);
    List<Project> searchProject(String keyword, User user);
}
