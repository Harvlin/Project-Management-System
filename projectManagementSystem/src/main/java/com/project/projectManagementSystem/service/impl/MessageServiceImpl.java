package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.entity.Chat;
import com.project.projectManagementSystem.domain.entity.Message;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.MessageRepository;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.MessageService;
import com.project.projectManagementSystem.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ProjectService projectService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("user not found with id: " + senderId));
        Chat chat = projectService.getChatByProjectId(projectId);
        if (chat == null) {
            throw new EntityNotFoundException("chat not found with id: " + projectId);
        }
        Message message = Message.builder()
                .sender(sender)
                .chat(chat)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) {
        Chat chat = projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;
    }
}
