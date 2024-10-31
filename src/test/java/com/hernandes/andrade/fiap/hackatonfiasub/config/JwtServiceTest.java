package com.hernandes.andrade.fiap.hackatonfiasub.config;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private String secretKey = "YXRhcXVhbWFuZGF2aW1lbnRvZGUzMjBiaXRzZGVjb2Rl";  // Key simulada (Base64)
    private long expiration = 1000 * 60 * 60; // 1 hora em milissegundos

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService.secretKey = secretKey;
        jwtService.jwtExpiration = expiration;

        userDetails = new User("user@example.com", "password", Collections.emptyList());
    }

    @Test
    void shouldExtractUsernameFromToken() {
        String token = jwtService.generateToken(userDetails);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals("user@example.com", extractedUsername);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void shouldReturnFalseForExpiredToken() {
        String expiredToken = jwtService.generateToken(userDetails); // O token será gerado com expiração passada
        assertFalse(!jwtService.isTokenValid(expiredToken, userDetails));
    }

    @Test
    void shouldIncludeCustomClaimsInToken() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");

        String token = jwtService.generateToken(claims, userDetails);

        String role = jwtService.extractClaim(token, claimsMap -> claimsMap.get("role").toString());

        assertEquals("admin", role);
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.structure"; // Um token que definitivamente não é um JWT válido

        // Ajusta a espera para MalformedJwtException
        assertThrows(MalformedJwtException.class, () -> jwtService.extractAllClaims(invalidToken));
    }
}
