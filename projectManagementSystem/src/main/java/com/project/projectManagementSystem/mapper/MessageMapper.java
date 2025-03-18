package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.MessageDto;
import com.project.projectManagementSystem.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MessageMapper {
    Message toEntity(MessageDto messageDto);
    MessageDto toDto(Message message);
}
