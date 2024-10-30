package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeType;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        UserRole role = new UserRole();
        role.setName("ROLE_USER");

        user = User.builder()
                .id(1)
                .name("Test User")
                .email("testuser@example.com")
                .password("password")
                .accountLocket(false)
                .enabled(true)
                .preference(ExchangeType.IN_PERSON)
                .roles(Arrays.asList(role))
                .build();
    }

    @Test
    void testUserCreation() {
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Test User");
        assertThat(user.getEmail()).isEqualTo("testuser@example.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
    }

    @Test
    void testGetAuthorities() {
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(user.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
    }

    @Test
    void testAccountNonLocked() {
        user.setAccountLocket(true);
        assertThat(user.isAccountNonLocked()).isFalse();
    }

    @Test
    void testIsEnabled() {
        user.setEnabled(false);
        assertThat(user.isEnabled()).isFalse();
    }
}
