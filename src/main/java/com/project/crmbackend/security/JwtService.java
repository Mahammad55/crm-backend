package com.project.crmbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final UserDetailsService userDetailsService;

    private Key key;

    @Value("${security.jwt.accessToken.secret}")
    private String accessTokenSecret;

    @Value("${security.jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @PostConstruct
    public void initializeKey() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        List<String> authorityList = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("Mahammad Ilyazov")
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(accessTokenExpiration))))
                .signWith(key, SignatureAlgorithm.HS256)
                .setHeader(Map.of("type", "JWT"))
                .claim("authority", authorityList)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Jwt expired exception : {}", expiredJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("JwtToken is null,empty or only whitespace : {}", illegalArgumentException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            log.error("Jwt is invalid : {}", malformedJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("Jwt is not supported : {}", unsupportedJwtException.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException signatureException) {
            log.error("Signature validation failed : {}", signatureException.getMessage());
        }
        return false;
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
        Claims claims = parseToken(token);
        return claimsFunction.apply(claims);
    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }
}
