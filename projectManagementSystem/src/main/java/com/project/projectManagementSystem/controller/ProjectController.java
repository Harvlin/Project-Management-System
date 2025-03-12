package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.domain.dto.ProjectDto;
import com.project.projectManagementSystem.domain.entity.Invitation;
import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.domain.request.InviteRequest;
import com.project.projectManagementSystem.domain.response.ResponseMessage;
import com.project.projectManagementSystem.mapper.ProjectMapper;
import com.project.projectManagementSystem.service.InvitationService;
import com.project.projectManagementSystem.service.ProjectService;
import com.project.projectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final InvitationService invitationService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, UserService userService, InvitationService invitationService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestHeader("Authorization") String jwt, @RequestBody ProjectDto projectDtoReq) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectMapper.toEntity(projectDtoReq);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(projectMapper.toDto(createdProject), HttpStatus.CREATED);
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
