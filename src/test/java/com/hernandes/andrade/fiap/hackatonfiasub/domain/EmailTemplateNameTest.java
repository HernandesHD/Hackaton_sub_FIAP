package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTemplateNameTest {

    @Test
    void testEnumValues() {
        assertEquals("activate_account", EmailTemplateName.ACTIVATE_ACCOUNT.getName());
        assertEquals("reset_password_template", EmailTemplateName.RESET_PASSWORD.getName());
        assertEquals("confirm_email_template", EmailTemplateName.CONFIRM_EMAIL.getName());
        assertEquals("exchange_notification", EmailTemplateName.EXCHANGE_NOTIFICATION.getName());
    }
}