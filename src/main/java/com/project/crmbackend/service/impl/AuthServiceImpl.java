package com.project.crmbackend.service.impl;

import com.project.crmbackend.dto.request.LoginRequest;
import com.project.crmbackend.dto.response.LoginResponse;
import com.project.crmbackend.entity.User;
import com.project.crmbackend.exception.NotFoundException;
import com.project.crmbackend.mapper.UserMapper;
import com.project.crmbackend.repository.UserRepository;
import com.project.crmbackend.security.JwtService;
import com.project.crmbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserMapper mapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User by email=%s not found".formatted(email)));

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return new LoginResponse(jwtService.generateToken(authenticate), mapper.entityToResponse(user));
    }
}
