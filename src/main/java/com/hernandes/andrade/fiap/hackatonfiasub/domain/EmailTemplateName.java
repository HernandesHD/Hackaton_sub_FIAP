package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("reset_password_template"),
    CONFIRM_EMAIL("confirm_email_template"),
    EXCHANGE_NOTIFICATION("exchange_notification");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
