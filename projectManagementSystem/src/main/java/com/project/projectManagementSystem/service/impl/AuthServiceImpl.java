package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.dto.UserDto;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.mapper.UserMapper;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto signup(UserDto userDto) {
        System.out.println("Input UserDto: " + userDto);

        // Check if email exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists with another account");
        }

        // Convert DTO to entity using mapper
        User userEntity = userMapper.toEntity(userDto);
        System.out.println("After mapping to entity: " + userEntity);

        // Encode password
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        System.out.println("After password encoding: " + userEntity);

        // Save user and return the DTO
        User savedUser = userRepository.save(userEntity);
        System.out.println("After saving: " + savedUser);

        UserDto resultDto = userMapper.toDto(savedUser);
        System.out.println("Result DTO: " + resultDto);
        return resultDto;
    }
}