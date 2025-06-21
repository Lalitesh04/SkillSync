package com.klef.jfsd.userservice.service.impl;

import com.klef.jfsd.userservice.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    private final SecretKey secretKey;

    public JWTServiceImpl(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = secret.getBytes();
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().stream()
                        .findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElse("NULL"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofDays(30).toMillis())) // 1 hour
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject().equals(userDetails.getUsername()) &&
                claims.getExpiration().after(new Date());
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    @Override
    public String generateVerificationToken(String email) {
       return Jwts.builder()
               .subject(email)
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(10).toMillis()))
               .signWith(secretKey)
               .compact();

    }
}
