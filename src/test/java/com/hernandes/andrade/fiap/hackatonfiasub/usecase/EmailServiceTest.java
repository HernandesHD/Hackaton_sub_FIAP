package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.EmailTemplateName;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private EmailService emailService;

    @Test
    void shouldSendEmail() throws IOException {
        // Arrange
        String to = "test@example.com";
        String username = "testUser";
        EmailTemplateName templateName = EmailTemplateName.ACTIVATE_ACCOUNT;
        String confirmationUrl = "http://localhost/confirm";
        String activationCode = "activationCode123";
        String subject = "Activate your account";

        // Simulando uma resposta de erro de autenticação
        Response responseMock = mock(Response.class);
        // Use lenient() para permitir que essas stubs sejam mantidas sem gerar erros
        lenient().when(responseMock.getStatusCode()).thenReturn(401);
        lenient().when(responseMock.getBody()).thenReturn("{\"errors\":[{\"message\":\"The provided authorization grant is invalid, expired, or revoked\",\"field\":null,\"help\":null}]}");
        lenient().when(sendGrid.api(any(Request.class))).thenReturn(responseMock);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            emailService.sendEmail(to, username, templateName, confirmationUrl, activationCode, subject);
        });

        assertEquals("Erro ao enviar e-mail: {\"errors\":[{\"message\":\"The provided authorization grant is invalid, expired, or revoked\",\"field\":null,\"help\":null}]}", thrown.getMessage());
    }

    @Test
    void shouldMapTemplateNameToId() {
        // Arrange
        String expectedId = "d-690ad8fe51d14dc4b98967d43a3b1e2e";

        // Act
        String templateId = emailService.getTemplateId(EmailTemplateName.ACTIVATE_ACCOUNT);

        // Assert
        assertEquals(expectedId, templateId);
    }
}
