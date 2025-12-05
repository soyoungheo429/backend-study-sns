package com.example.devSns.service;

import com.example.devSns.config.JwtService;
import com.example.devSns.domain.User;
import com.example.devSns.dto.AuthRequest;
import com.example.devSns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String register(AuthRequest request) {
        validateCredentials(request);
        userRepository.findByUsername(request.username())
                .ifPresent(user -> { throw new IllegalArgumentException("Username already exists"); });

        User user = new User(request.username(), request.password(), "USER");
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String login(AuthRequest request) {
        validateCredentials(request);
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtService.generateToken(user);
    }

    private void validateCredentials(AuthRequest request) {
        if (!StringUtils.hasText(request.username()) || !StringUtils.hasText(request.password())) {
            throw new IllegalArgumentException("Username and password are required");
        }
    }
}