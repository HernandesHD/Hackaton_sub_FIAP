package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.AuthenticationService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() throws MessagingException {
        RegistrationRequest request = new RegistrationRequest();
        // Populate the request object with necessary data

        ResponseEntity<?> response = authController.register(request);

        verify(authenticationService).register(request);
        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    void shouldLoginUser() {
        LoginRequest loginRequest = new LoginRequest();
        // Populate the loginRequest object with necessary data
        LoginResponse expectedResponse = new LoginResponse();
        when(authenticationService.login(loginRequest)).thenReturn(expectedResponse);

        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        verify(authenticationService).login(loginRequest);
        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldActivateAccount() throws MessagingException {
        String token = "someToken";

        ResponseEntity<?> response = authController.activateAccount(token);

        verify(authenticationService).activateAccount(token);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldHandleForgotPassword() throws MessagingException {
        String email = "user@example.com";

        ResponseEntity<?> response = authController.forgotPassword(email);

        verify(authenticationService).forgotPassword(email);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldResetPassword() {
        String token = "someToken";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        // Populate the resetPasswordRequest object with necessary data

        ResponseEntity<?> response = authController.resetPassword(token, resetPasswordRequest);

        verify(authenticationService).resetPassword(token, resetPasswordRequest);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldReturnHelloWorld() {
        String response = authController.helloWord();

        assertEquals("OLA MUNDO!", response);
    }
}