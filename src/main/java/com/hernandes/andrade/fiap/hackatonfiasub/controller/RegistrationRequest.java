package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "E-mail is required")
    @NotBlank(message = "E-mail is required")
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    @NotNull(message = "Exchange Type is required")
    private ExchangeType exchangeType;
}
