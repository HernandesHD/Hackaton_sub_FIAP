package com.hernandes.andrade.fiap.hackatonfiasub.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    @WithMockUser // Simula um usuário autenticado
    void shouldConfigureSecurityFilterChain() throws Exception {
        // Use um método para obter a configuração do HttpSecurity.
        // Verifique a configuração que foi aplicada ao HttpSecurity.
        // Não é possível verificar diretamente o filtro, mas você pode testar as permissões aqui.

        // Exemplo de teste de permissões:
        assertThat(securityConfig).isNotNull(); // Verifique se a configuração foi carregada corretamente
    }
}