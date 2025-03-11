package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.dto.UserDto;

public interface AuthService {
    UserDto signup(UserDto userDto);
}
