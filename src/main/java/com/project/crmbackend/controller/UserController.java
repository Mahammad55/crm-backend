package com.project.crmbackend.controller;

import com.project.crmbackend.dto.request.UserRequest;
import com.project.crmbackend.dto.response.UserResponse;
import com.project.crmbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(CREATED).body(userService.createUser(userRequest));
    }
}
