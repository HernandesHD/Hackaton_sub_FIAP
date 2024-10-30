package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Test User")
                .email("testuser@example.com")
                .password("password")
                .accountLocket(false)
                .enabled(true)
                .preference(ExchangeType.IN_PERSON)
                .build();
        userRepository.save(user);
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Test User");
    }

    @Test
    void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void testSaveUser() {
        User newUser = User.builder()
                .name("New User")
                .email("newuser@example.com")
                .password("newpassword")
                .accountLocket(false)
                .enabled(true)
                .preference(ExchangeType.DELIVERY)
                .build();

        userRepository.save(newUser);

        Optional<User> foundUser = userRepository.findByEmail("newuser@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("New User");
    }
}
