package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.IssueDto;
import com.project.projectManagementSystem.domain.entity.Issue;
import com.project.projectManagementSystem.domain.request.IssueRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IssueMapper {
    Issue toEntity(IssueDto issueDto);
    IssueDto toDto(Issue issue);
    Issue fromRequest(IssueRequest issueRequest);
}
