package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.config.JwtProvider;
import com.project.projectManagementSystem.domain.dto.UserDto;
import com.project.projectManagementSystem.domain.request.LoginRequest;
import com.project.projectManagementSystem.domain.response.AuthResponse;
import com.project.projectManagementSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDto userDto) {
        authService.signup(userDto);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("Signup successful", jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) {
        log.info("Login attempt with email: {}", loginRequest.getEmail());

        try {
            Authentication authentication = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = JwtProvider.generateToken(authentication);

            return ResponseEntity.ok(new AuthResponse("Login successful", jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Invalid username or password", null));
        }
    }

}
