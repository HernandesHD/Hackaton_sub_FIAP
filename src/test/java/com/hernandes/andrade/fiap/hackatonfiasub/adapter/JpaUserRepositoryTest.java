package com.hernandes.andrade.fiap.hackatonfiasub.adapter;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeType;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaUserRepositoryTest {
    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        // Arrange - Criar e salvar um usuário no banco
        User user = new User();
        user.setName("Hernandes");
        user.setEmail("cfhernandesandrade@gmail.com");
        user.setPassword("password123");
        user.setPreference(ExchangeType.IN_PERSON);
        userRepository.save(user);

        // Act - Buscar o usuário pelo e-mail
        Optional<User> foundUser = userRepository.findByEmail("cfhernandesandrade@gmail.com");

        // Assert - Verificar se o usuário foi encontrado
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Hernandes");
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        // Act - Tentar buscar um e-mail inexistente
        Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");

        // Assert - Verificar que o resultado é vazio
        assertThat(foundUser).isEmpty();
    }
}
