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

        Response responseMock = mock(Response.class);
        when(responseMock.getStatusCode()).thenReturn(202);
        when(responseMock.getBody()).thenReturn("");
        when(sendGrid.api(any())).thenReturn(responseMock);

        // Act
        emailService.sendEmail(to, username, templateName, confirmationUrl, activationCode, subject);

        // Assert
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(sendGrid).api(requestCaptor.capture());

        Request sentRequest = requestCaptor.getValue();
        assertEquals("mail/send", sentRequest.getEndpoint());
        assertEquals(Method.POST, sentRequest.getMethod());
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
