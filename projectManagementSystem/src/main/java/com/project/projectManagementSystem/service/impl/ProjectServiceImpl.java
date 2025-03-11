package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Chat;
import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.ProjectRepository;
import com.project.projectManagementSystem.service.ChatService;
import com.project.projectManagementSystem.service.ProjectService;
import com.project.projectManagementSystem.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, ChatService chatService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Project createProject(Project project, User user) {
        project.getTeam().add(user);
        Chat chat = new Chat();
        chat.setProject(project);
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found"));
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
        } else {
            throw new EntityNotFoundException("Project with id " + projectId + " not found");
        }
    }

    @Override
    public Project updateProject(Long id, Project project) {
        return projectRepository.findById(id).map(existingProject -> {
            Optional.ofNullable(project.getName()).ifPresent(existingProject::setName);
            Optional.ofNullable(project.getDescription()).ifPresent(existingProject::setDescription);
            Optional.ofNullable(project.getTags()).ifPresent(existingProject::setTags);
            Optional.ofNullable(project.getCategory()).ifPresent(existingProject::setCategory);
            return projectRepository.save(existingProject);
        }).orElseThrow(() -> new EntityNotFoundException("Project does not exist"));
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getTeam().add(user);
            project.getChat().getUsers().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }
}
