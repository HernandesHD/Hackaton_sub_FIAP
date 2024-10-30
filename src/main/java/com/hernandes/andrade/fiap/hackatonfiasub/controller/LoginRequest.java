package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Minimo 8 caracteres.")
    private String password;

}
