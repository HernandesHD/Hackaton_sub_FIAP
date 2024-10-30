package com.hernandes.andrade.fiap.hackatonfiasub.adapter;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        // Arrange - Criar um usuário simulado e configurar o comportamento do mock
        User user = new User();
        user.setEmail("cfhernandesandrade@gmail.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("cfhernandesandrade@gmail.com"))
                .thenReturn(Optional.of(user));

        // Act - Chamar o método de teste
        UserDetails userDetails = userDetailsService.loadUserByUsername("cfhernandesandrade@gmail.com");

        // Assert - Verificar se o resultado é correto
        assertNotNull(userDetails);
        assertEquals("cfhernandesandrade@gmail.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());

        // Verificar se o método do repositório foi chamado corretamente
        verify(userRepository, times(1)).findByEmail("cfhernandesandrade@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange - Configurar o mock para retornar vazio
        when(userRepository.findByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());

        // Act & Assert - Verificar se a exceção é lançada
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("notfound@example.com")
        );

        assertEquals("User not found with e-mail: notfound@example.com", exception.getMessage());

        // Verificar se o método do repositório foi chamado corretamente
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }
}
