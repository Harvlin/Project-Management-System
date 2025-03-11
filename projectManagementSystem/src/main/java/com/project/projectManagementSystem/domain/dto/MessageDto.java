package com.project.projectManagementSystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;
    private ChatDto chatDto;
}
