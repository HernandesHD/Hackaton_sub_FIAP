package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginResponseTest {

    @Test
    void testLoginResponse() {
        String token = "sample_token";

        // Criação da instância usando o Builder
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .build();

        // Verificar se o token foi setado corretamente
        assertEquals(token, response.getToken(), "Token should match the input value");
    }
}