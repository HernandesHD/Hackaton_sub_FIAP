package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Token;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.TokenRepository;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    private User user;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Criar e salvar um usuário para associar ao token
        user = new User();
        user.setName("Test User");
        // Supondo que você tenha um repositório para User e o salvará
        // userRepository.save(user); // Descomente isso se você tiver um repositório de usuário
    }

    @Test
    void testSaveAndFindToken() {
        User user = new User();
        user.setName("User Name");
        user.setEmail("teste@email.com");
        user.setPassword("password123");
        user.setPreference(ExchangeType.IN_PERSON);
        user = userRepository.save(user);

        Token token = Token.builder()
                .token("test-token")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .validatedAt(null)
                .user(user)
                .build();

        tokenRepository.save(token); // Salve o token

        Optional<Token> foundToken = tokenRepository.findByToken("test-token");
        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo("test-token");
    }

    @Test
    void testFindByTokenNotFound() {
        Optional<Token> foundToken = tokenRepository.findByToken("non-existing-token");
        assertThat(foundToken).isNotPresent();
    }
}
