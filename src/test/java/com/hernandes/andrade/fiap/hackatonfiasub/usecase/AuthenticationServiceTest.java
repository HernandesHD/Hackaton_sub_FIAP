package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.RegistrationRequest;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldRegisterUserAndSendValidationEmail() throws MessagingException, IOException {
        // Arrange
        var request = new RegistrationRequest("testUser", "test@example.com", "password", ExchangeType.IN_PERSON);
        var userRole = new UserRole("USER");

        when(userRoleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        authenticationService.register(request);

        // Assert
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("testUser", savedUser.getName(), "O username não está correto.");
        assertEquals("test@example.com", savedUser.getEmail(), "O email não está correto.");
        assertEquals("encodedPassword", savedUser.getPassword(), "A senha codificada não corresponde.");
        //assertEquals(userRole, savedUser.getRoles().equals("USER"), "O papel do usuário não corresponde.");

        verify(emailService).sendEmail(
                eq("test@example.com"),
                eq("testUser"),
                eq(EmailTemplateName.ACTIVATE_ACCOUNT),
                eq(null),
                tokenCaptor.capture(),
                eq("Activate your account")
        );

        String capturedToken = tokenCaptor.getValue();
        assertNotNull(capturedToken, "O token não deveria ser nulo.");
        assertFalse(capturedToken.isEmpty(), "O token não deveria ser vazio.");

        verify(tokenRepository).save(any());
    }
}