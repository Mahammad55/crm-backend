package com.project.crmbackend.service.impl;

import com.project.crmbackend.entity.Authority;
import com.project.crmbackend.enums.UserRole;
import com.project.crmbackend.repository.AuthorityRepository;
import com.project.crmbackend.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthority(UserRole userRole) {
        return authorityRepository.findAuthorityByAuthority(userRole)
                .orElseGet(() -> saveAuthority(userRole));
    }

    @Override
    public Authority saveAuthority(UserRole userRole) {
        Authority authority = new Authority();
        authority.setAuthority(userRole);
        return authorityRepository.save(authority);
    }
}
