package com.project.crmbackend.service;

import com.project.crmbackend.dto.request.LoginRequest;
import com.project.crmbackend.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
