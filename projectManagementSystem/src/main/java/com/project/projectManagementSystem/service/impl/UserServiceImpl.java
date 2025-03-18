package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.config.JwtProvider;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email " + email));
        return user;
    }

    @Override
    public User findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return user;
    }

    @Override
    public User updateUserProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
