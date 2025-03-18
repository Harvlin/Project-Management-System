package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long projectId, String content);
    List<Message> getMessagesByProjectId(Long projectId);
}
