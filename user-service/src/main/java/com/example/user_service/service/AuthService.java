package com.example.user_service.service;

import com.example.user_service.config.JwtService;
import com.example.user_service.dto.*;
import com.example.user_service.domain.entity.User;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request) {
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Optional.ofNullable(request.getRole()).orElse("USER"))
                .build();

        userRepository.save(newUser);

        return new RegisterResponse("User registered successfully");
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new LoginResponse(jwtService.generateToken(user));
    }

}
