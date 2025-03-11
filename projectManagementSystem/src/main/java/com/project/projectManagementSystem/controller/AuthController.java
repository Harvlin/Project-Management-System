package com.project.projectManagementSystem.controller;

import com.project.projectManagementSystem.config.JwtProvider;
import com.project.projectManagementSystem.domain.dto.UserDto;
import com.project.projectManagementSystem.domain.response.AuthResponse;
import com.project.projectManagementSystem.service.AuthService;
import com.project.projectManagementSystem.service.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomUserDetailsImpl customUserDetails;

    @Autowired
    public AuthController(AuthService authService, CustomUserDetailsImpl customUserDetails) {
        this.authService = authService;
        this.customUserDetails = customUserDetails;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@RequestBody UserDto userDto) throws Exception {
        System.out.println("Received signup request: " + userDto);

        UserDto savedUser = authService.signup(userDto);
        System.out.println("User saved: " + savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Signup successful");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED); // Return the AuthResponse
    }
}
