package com.project.crmbackend.service.impl;

import com.project.crmbackend.dto.request.UserRequest;
import com.project.crmbackend.dto.response.UserResponse;
import com.project.crmbackend.entity.Authority;
import com.project.crmbackend.entity.User;
import com.project.crmbackend.exception.AlreadyExistsException;
import com.project.crmbackend.exception.NotFoundException;
import com.project.crmbackend.mapper.UserMapper;
import com.project.crmbackend.repository.UserRepository;
import com.project.crmbackend.service.AuthorityService;
import com.project.crmbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authorityService;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> {
                            log.error("UserServiceImpl.findUserByUsername.error -- user not found email:{}", username);
                            return new NotFoundException("User by username=%s not found".formatted(username));
                        }
                );
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.findUserByEmail(userRequest.getEmail()).isPresent()) {
            throw new AlreadyExistsException("User by email=%s already exist".formatted(userRequest.getEmail()));
        }

        User user = userMapper.requestToEntity(userRequest);
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = authorityService.getAuthority(userRequest.getUserRole());
        user.setAuthorities(Set.of(authority));
        return userMapper.entityToResponse(userRepository.save(user));
    }
}
