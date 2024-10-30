package com.hernandes.andrade.fiap.hackatonfiasub.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageSourceHelperTest {

    @Mock
    private static MessageSource messageSource;

    @InjectMocks
    private MessageSourceHelper messageSourceHelper;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(MessageSourceHelperTest.class);
        new MessageSourceHelper(messageSource); // Injeção do mock
    }

    @Test
    void shouldReturnMessageForGivenKey() {
        LocaleContextHolder.setLocale(new Locale("pt", "BR"));
        when(messageSource.getMessage("greeting", null, LocaleContextHolder.getLocale()))
                .thenReturn("Olá!");
        String actualMessage = MessageSourceHelper.getMessage("greeting");
        assertEquals("Olá!", actualMessage);
    }

    @Test
    void shouldHandleMissingMessageGracefully() {
        LocaleContextHolder.setLocale(new Locale("pt", "BR"));

        when(messageSource.getMessage(any(), any(), any()))
                .thenReturn("Mensagem não encontrada");

        String actualMessage = MessageSourceHelper.getMessage("invalid.key");

        assertEquals("Mensagem não encontrada", actualMessage);
    }
}