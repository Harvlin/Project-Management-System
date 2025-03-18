package com.project.projectManagementSystem.domain.request;

import lombok.Data;

@Data
public class CreateMessageRequest {
    private Long senderId;
    private String content;
    private Long projectId;
}
