package com.project.crmbackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.project.crmbackend.security.HttpConstants.AUTH_HEADER;
import static com.project.crmbackend.security.HttpConstants.BEARER_AUTH_HEADER;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTH_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_AUTH_HEADER)) {
            String jwtToken = authorizationHeader.substring(BEARER_AUTH_HEADER.length());
            if (jwtService.validateToken(jwtToken)) {
                String username = jwtService.extractClaims(jwtToken, Claims::getSubject);
                Authentication authentication = jwtService.getAuthentication(username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
