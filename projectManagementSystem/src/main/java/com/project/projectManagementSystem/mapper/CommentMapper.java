package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.CommentsDto;
import com.project.projectManagementSystem.domain.entity.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper {
    Comments toEntity(CommentsDto commentsDto);
    CommentsDto toDto(Comments comments);
}
