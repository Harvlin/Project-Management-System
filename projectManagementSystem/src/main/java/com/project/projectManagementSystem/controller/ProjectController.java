package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.domain.dto.ChatDto;
import com.project.projectManagementSystem.domain.dto.ProjectDto;
import com.project.projectManagementSystem.domain.entity.Invitation;
import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.domain.request.InviteRequest;
import com.project.projectManagementSystem.domain.response.ResponseMessage;
import com.project.projectManagementSystem.mapper.ChatMapper;
import com.project.projectManagementSystem.mapper.ProjectMapper;
import com.project.projectManagementSystem.service.InvitationService;
import com.project.projectManagementSystem.service.ProjectService;
import com.project.projectManagementSystem.service.UserService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final InvitationService invitationService;
    private final ChatMapper chatMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, UserService userService, InvitationService invitationService, ChatMapper chatMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.invitationService = invitationService;
        this.chatMapper = chatMapper;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestHeader("Authorization") String jwt, @RequestBody ProjectDto projectDtoReq) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectMapper.toEntity(projectDtoReq);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(projectMapper.toDto(createdProject), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestHeader("Authorization") String jwt, @RequestParam(required = false) String category, @RequestParam(required = false) String tag) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        List<ProjectDto> projectDtos = projects.stream().map(projectMapper::toDto).toList();
        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt) throws Exception {
            ProjectDto projectDto = projectMapper.toDto(projectService.getProjectById(projectId));
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProjectDto>> searchProject(@PathVariable(required = false) String keyword, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);
        List<ProjectDto> projectDtos = projects.stream().map(projectMapper::toDto).toList();
        return new ResponseEntity<>(projectDtos, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ChatDto> getChatByProjectId(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        ChatDto chatDto = chatMapper.toDto(projectService.getChatByProjectId(projectId));
        return new ResponseEntity<>(chatDto, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDtoReq, @RequestHeader("Authorization") String jwt) throws Exception {
        Project project = projectService.updateProject(projectId, projectMapper.toEntity(projectDtoReq));
        return new ResponseEntity<>(projectMapper.toDto(project), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ResponseMessage> deleteProject(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt, ServletRequest servletRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        ResponseMessage response = new ResponseMessage("Project deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/invite")
    public ResponseEntity<ResponseMessage> inviteProject(@RequestHeader("Authorization") String jwt, @RequestBody InviteRequest inviteRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(inviteRequest.getEmail(), inviteRequest.getProjectId());
        ResponseMessage response = new ResponseMessage("User invitation sent");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/accept_invitation")
    public ResponseEntity<ResponseMessage> acceptInvitation(@RequestHeader("Authorization") String jwt, @RequestParam String token) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        ResponseMessage response = new ResponseMessage("User invitation sent");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
