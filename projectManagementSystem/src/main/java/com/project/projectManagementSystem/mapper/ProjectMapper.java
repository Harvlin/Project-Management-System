package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.ProjectDto;
import com.project.projectManagementSystem.domain.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);
    ProjectDto toDto(Project project);
}
