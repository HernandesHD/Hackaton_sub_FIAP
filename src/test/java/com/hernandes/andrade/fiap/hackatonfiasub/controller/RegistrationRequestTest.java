package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationRequestTest {

    private final Validator validator;

    public RegistrationRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateSuccessfully() {
        RegistrationRequest request = RegistrationRequest.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("securePassword")
                .exchangeType(ExchangeType.IN_PERSON)
                .build();

        Set violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Validation should pass without violations");
    }

    @Test
    void shouldFailValidationWhenNameIsNull() {
        RegistrationRequest request = RegistrationRequest.builder()
                .name(null)
                .email("john.doe@example.com")
                .password("securePassword")
                .exchangeType(ExchangeType.IN_PERSON)
                .build();

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when name is null");
    }

    @Test
    void shouldFailValidationWhenEmailIsBlank() {
        RegistrationRequest request = RegistrationRequest.builder()
                .name("John Doe")
                .email("")
                .password("securePassword")
                .exchangeType(ExchangeType.IN_PERSON)
                .build();

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when email is blank");
    }

    @Test
    void shouldFailValidationWhenPasswordIsTooShort() {
        RegistrationRequest request = RegistrationRequest.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("short")
                .exchangeType(ExchangeType.IN_PERSON)
                .build();

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when password is too short");
    }

    @Test
    void shouldFailValidationWhenExchangeTypeIsNull() {
        RegistrationRequest request = RegistrationRequest.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("securePassword")
                .exchangeType(null) // ExchangeType é nulo
                .build();

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when exchange type is null");
    }
}