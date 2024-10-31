package com.project.crmbackend.service;

import com.project.crmbackend.entity.Authority;
import com.project.crmbackend.enums.UserRole;

public interface AuthorityService {
    Authority getAuthority(UserRole userRole);

    Authority saveAuthority(UserRole userRole);
}
