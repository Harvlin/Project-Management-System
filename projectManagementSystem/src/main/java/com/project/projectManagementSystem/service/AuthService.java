package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    UserDto signup(UserDto userDto);
    Authentication authenticate(String email, String password);
}
