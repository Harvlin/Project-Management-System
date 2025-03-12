package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.ChatDto;
import com.project.projectManagementSystem.domain.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChatMapper {
    Chat toEntity(ChatDto chatDto);
    ChatDto toDto(Chat chat);
}
