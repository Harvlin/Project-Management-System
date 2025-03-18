package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.User;

public interface UserService {
    User findUserProfileByJwt(String jwt);
    User findUserByEmail(String email);
    User findUserById(Long id);
    User updateUserProjectSize(User user, int number);
}
