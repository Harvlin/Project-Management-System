package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.domain.dto.MessageDto;
import com.project.projectManagementSystem.domain.entity.Message;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.domain.request.CreateMessageRequest;
import com.project.projectManagementSystem.mapper.MessageMapper;
import com.project.projectManagementSystem.service.MessageService;
import com.project.projectManagementSystem.service.ProjectService;
import com.project.projectManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ProjectService projectService;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, ProjectService projectService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
        this.messageMapper = messageMapper;
    }

    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody CreateMessageRequest message) {
        Message sentMessage = messageService.sendMessage(message.getSenderId(), message.getProjectId(), message.getContent());
        MessageDto messageDto = messageMapper.toDto(sentMessage);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<MessageDto>> getMessagesByChatId(@PathVariable Long projectId) {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        List<MessageDto> messageDtos = messages.stream().map(messageMapper::toDto).toList();
        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }
}
