package com.hernandes.andrade.fiap.hackatonfiasub.config;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeansConfigTest {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService; // Autowired aqui para garantir que o bean seja injetado

    @Test
    void shouldLoadAuthenticationProviderBean() {
        assertNotNull(authenticationProvider);
    }

    @Test
    void shouldLoadAuthenticationManagerBean() {
        assertNotNull(authenticationManager);
    }

    @Test
    void shouldLoadPasswordEncoderBean() {
        assertNotNull(passwordEncoder);
    }

    @Test
    void shouldLoadUserDetailsServiceBean() {
        assertNotNull(userDetailsService); // Teste para garantir que o UserDetailsService está sendo carregado
    }
}