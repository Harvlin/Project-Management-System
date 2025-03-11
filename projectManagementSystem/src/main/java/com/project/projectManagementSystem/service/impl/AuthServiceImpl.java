package com.project.projectManagementSystem.service.impl;

import com.project.projectManagementSystem.domain.dto.UserDto;
import com.project.projectManagementSystem.domain.entity.User;
import com.project.projectManagementSystem.mapper.UserMapper;
import com.project.projectManagementSystem.repository.UserRepository;
import com.project.projectManagementSystem.service.AuthService;
import com.project.projectManagementSystem.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CustomUserDetailsImpl customUserDetails;

    @Override
    public UserDto signup(UserDto userDto) {
        log.info("Signing up user with email: {}", userDto.getEmail());

        userRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("Email already exists with another account");
        });

        User userEntity = userMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(userEntity);
        return userMapper.toDto(savedUser);
    }

    @Override
    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(email);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
