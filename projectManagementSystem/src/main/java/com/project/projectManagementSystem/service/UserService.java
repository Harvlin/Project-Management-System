package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long id) throws Exception;
    User updateUserProjectSize(User user, int number) throws Exception;
}
