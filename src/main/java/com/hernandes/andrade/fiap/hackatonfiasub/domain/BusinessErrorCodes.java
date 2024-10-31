package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.config.MessageSourceHelper;
import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    NO_CODE(0, NOT_IMPLEMENTED, "exceptionEnum.noCode"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "exceptionEnum.incorrectPassword"),
    NEW_PASSWORD_NOT_MATCH(301, BAD_REQUEST, "exceptionEnum.newPasswordNotMatch"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "exceptionEnum.accountLocked"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "exceptionEnum.accountDisabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "exceptionEnum.badCredentials"),
    DUPLICATE_KEY(305, CONFLICT, "exceptionEnum.duplicateKey"),
    INTERNAL_ERROR(500, INTERNAL_SERVER_ERROR, "exceptionEnum.internalServerError"),
    ;

    @Getter
    private final int code;
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private static ResourceBundleMessageSource messageSource;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String getDescription(Object... args) {
        return getMessage(description, args);
    }

    public static void setMessageSource(ResourceBundleMessageSource messageSource) {
        BusinessErrorCodes.messageSource = messageSource;
    }

    private static String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    public String getDescription() {
        return MessageSourceHelper.getMessage(description);
    }

}
