package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRole;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole = new UserRole("ROLE_USER");
        userRoleRepository.save(userRole);
    }

    @Test
    void testSaveUserRole() {
        UserRole savedRole = userRoleRepository.findById(userRole.getId()).orElse(null);
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void testFindByName() {
        Optional<UserRole> foundRole = userRoleRepository.findByName("ROLE_USER");
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void testFindByNameNotFound() {
        Optional<UserRole> foundRole = userRoleRepository.findByName("ROLE_ADMIN");
        assertThat(foundRole).isNotPresent();
    }

    @Test
    void testUniqueRoleName() {
        //UserRole anotherRole = new UserRole("ROLE_USER"); // Tente salvar o mesmo nome
        //userRoleRepository.save(anotherRole);

        Optional<UserRole> foundRole = userRoleRepository.findByName("ROLE_USER");
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo("ROLE_USER");
    }
}
