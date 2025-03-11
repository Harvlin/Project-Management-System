package com.project.projectManagementSystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private Long id;
    private String name;
    private ProjectDto project;
    private List<MessageDto> messageDtos;
    private List<UserDto> userDtos = new ArrayList<>();
}
