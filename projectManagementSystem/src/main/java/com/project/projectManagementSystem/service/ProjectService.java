package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Chat;
import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;
    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;
    Project getProjectById(Long id) throws Exception;
    void deleteProject(Long projectId, Long userId) throws Exception;
    Project updateProject(Long id, Project updatedProject) throws Exception;
    void addUserToProject(Long projectId, Long userId) throws Exception;
    void removeUserFromProject(Long projectId, Long userId) throws Exception;
    Chat getChatByProjectId(Long projectId) throws Exception;
    List<Project> searchProject(String keyword, User user) throws Exception;
}
