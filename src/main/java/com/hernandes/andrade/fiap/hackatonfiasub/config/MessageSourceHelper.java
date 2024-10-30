package com.hernandes.andrade.fiap.hackatonfiasub.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceHelper {

    private static MessageSource messageSource;

    public MessageSourceHelper(MessageSource messageSource) {
        MessageSourceHelper.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

}
