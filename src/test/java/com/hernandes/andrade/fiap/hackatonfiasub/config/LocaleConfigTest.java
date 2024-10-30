package com.hernandes.andrade.fiap.hackatonfiasub.config;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.BusinessErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

class LocaleConfigTest {

    private final LocaleConfig localeConfig = new LocaleConfig();

    @Test
    void shouldLoadMessageSourceWithUTF8Encoding() {
        MessageSource messageSource = localeConfig.messageSource();

        assertNotNull(messageSource);
        String expectedMessage = "Bem-vindo!"; // Simula uma mensagem que deveria estar no arquivo messages_pt_BR.properties

        LocaleContextHolder.setLocale(new Locale("pt", "BR"));
        String actualMessage = messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale());

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldSetMessageSourceInBusinessErrorCodes() {
        MessageSource messageSource = localeConfig.messageSource();

        assertEquals(messageSource, BusinessErrorCodes.getMessageSource());
    }
}