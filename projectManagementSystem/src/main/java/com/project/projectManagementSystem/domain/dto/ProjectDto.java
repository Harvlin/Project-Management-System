package com.project.projectManagementSystem.domain.dto;

import com.project.projectManagementSystem.domain.entity.Chat;
import com.project.projectManagementSystem.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> tags = new ArrayList<>();
    private UserDto owner;
    private List<IssueDto> issueDtos = new ArrayList<>();
    private Chat chat;
}
