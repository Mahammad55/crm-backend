package com.project.crmbackend.repository;

import com.project.crmbackend.entity.Authority;
import com.project.crmbackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findAuthorityByAuthority(UserRole userRole);
}
