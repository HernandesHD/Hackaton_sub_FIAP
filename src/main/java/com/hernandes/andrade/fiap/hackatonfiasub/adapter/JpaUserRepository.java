package com.hernandes.andrade.fiap.hackatonfiasub.adapter;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
