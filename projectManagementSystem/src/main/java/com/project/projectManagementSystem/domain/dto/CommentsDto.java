package com.project.projectManagementSystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsDto {
    private Long id;
    private LocalDateTime createdDateTime;
    private UserDto userDto;
    private IssueDto issueDto;
}
