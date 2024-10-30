package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.EmailTemplateName;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${spring.sendgrid.from.email}")
    private String fromEmail;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws IOException {
        // send email
        String templateId = getTemplateId(emailTemplateName);

        Map<String, Object> dynamicTemplateData = new HashMap<>();
        dynamicTemplateData.put("username", username);
        dynamicTemplateData.put("confirmationUrl", confirmationUrl);
        dynamicTemplateData.put("activationCode", activationCode);

        sendDynamicTemplateEmail(to, subject, templateId, dynamicTemplateData);
    }

    @Async
    public void sendEmailExchangeNotification(
            String to,
            String ownerId,
            String username,
            EmailTemplateName emailTemplateName,
            String requestedGameTitle,
            String requestedGameDescription,
            String requestedGamePlatform,
            String offeredGameTitle,
            String offeredGameDescription,
            String offeredGamePlatform,
            String subject
    ) throws IOException {
        // send email
        String templateId = getTemplateId(emailTemplateName);

        Map<String, Object> dynamicTemplateData = new HashMap<>();
        dynamicTemplateData.put("ownerId", ownerId);
        dynamicTemplateData.put("username", username);
        dynamicTemplateData.put("requestedGameTitle", requestedGameTitle);
        dynamicTemplateData.put("requestedGameDescription", requestedGameDescription);
        dynamicTemplateData.put("requestedGamePlatform", requestedGamePlatform);
        dynamicTemplateData.put("offeredGameTitle", offeredGameTitle);
        dynamicTemplateData.put("offeredGameDescription", offeredGameDescription);
        dynamicTemplateData.put("offeredGamePlatform", offeredGamePlatform);

        sendDynamicTemplateEmail(to, subject, templateId, dynamicTemplateData);
    }

    String getTemplateId(EmailTemplateName emailTemplateName) {
        // Mapear o nome do template para o ID do template sendgrid
        switch (emailTemplateName) {
            case ACTIVATE_ACCOUNT:
                return "d-690ad8fe51d14dc4b98967d43a3b1e2e";
            case RESET_PASSWORD:
                return "";
            case EXCHANGE_NOTIFICATION:
                return "d-bc6da7055f5d4b16801e6e4c5d5a716d";
            default:
                return "d-690ad8fe51d14dc4b98967d43a3b1e2e";
        }
    }

    private void sendDynamicTemplateEmail(String to, String subject, String templateId, Map<String, Object> dynamicTemplateData) throws IOException {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(templateId);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);
        personalization.setSubject(subject);

        for (Map.Entry<String, Object> entry : dynamicTemplateData.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        mail.addPersonalization(personalization);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
            System.out.println("Response headers: " + response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }

    }

}
