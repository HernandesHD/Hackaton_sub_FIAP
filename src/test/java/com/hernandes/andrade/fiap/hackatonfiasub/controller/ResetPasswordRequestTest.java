package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ResetPasswordRequestTest {

    private final Validator validator;

    public ResetPasswordRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateSuccessfully() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setNewPassword("securePassword");

        Set violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Validation should pass without violations");
    }

    @Test
    void shouldFailValidationWhenNewPasswordIsEmpty() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setNewPassword("");

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when new password is empty");
    }

    @Test
    void shouldFailValidationWhenNewPasswordIsTooShort() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setNewPassword("short");

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail when new password is too short");
    }
}