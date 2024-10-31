package com.project.crmbackend.service;

import com.project.crmbackend.dto.request.UserRequest;
import com.project.crmbackend.dto.response.UserResponse;
import com.project.crmbackend.entity.User;

public interface UserService {
    User findUserByUsername(String username);

    UserResponse createUser(UserRequest userRequest);
}
