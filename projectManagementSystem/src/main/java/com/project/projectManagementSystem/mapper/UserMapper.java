package com.project.projectManagementSystem.mapper;

import com.project.projectManagementSystem.domain.dto.UserDto;
import com.project.projectManagementSystem.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
