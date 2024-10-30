package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldValidateLoginRequestSuccessfully() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("strongPassword")
                .build();

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "LoginRequest should be valid");
    }

    @Test
    void shouldFailWhenEmailIsNull() {
        LoginRequest request = LoginRequest.builder()
                .email(null)
                .password("strongPassword")
                .build();
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        // Filtra apenas as violações para o campo "email"
        long emailViolations = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("email"))
                .count();
        assertFalse(violations.isEmpty(), "LoginRequest should not be valid when email is null");
        assertEquals(1, emailViolations, "There should be exactly one violation for the email field");
        String message = violations.iterator().next().getMessage();
        assertEquals("E-mail é obrigatório", message);
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        LoginRequest request = LoginRequest.builder()
                .email("")
                .password("strongPassword")
                .build();

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "LoginRequest should not be valid when email is blank");
        assertEquals(1, violations.size());
        assertEquals("E-mail é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPasswordIsNull() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password(null)
                .build();

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "LoginRequest should not be valid when password is null");
        assertEquals(1, violations.size());
        assertEquals("Senha é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("") // Password is blank
                .build();

        // Valida o objeto LoginRequest
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        // Exibir todas as violações para depuração
        violations.forEach(v -> System.out.println("Campo: " + v.getPropertyPath() + " - Erro: " + v.getMessage()));

        // Verifica se há violações
        assertFalse(violations.isEmpty(), "LoginRequest should not be valid when password is blank");
        assertEquals(2, violations.size(), "There should be exactly one violation");
        assertEquals("Senha é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPasswordIsTooShort() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("short")
                .build();

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "LoginRequest should not be valid when password is too short");
        assertEquals(1, violations.size());
        assertEquals("Minimo 8 caracteres.", violations.iterator().next().getMessage());
    }

}