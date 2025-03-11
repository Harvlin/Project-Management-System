package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.domain.entity.User;
import org.aspectj.apache.bcel.generic.LineNumberGen;

import java.util.Optional;

public interface UserService {
    User findUserByUserByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long id) throws Exception;
    User updateUserProjectSize(User user, int number) throws Exception;
}
