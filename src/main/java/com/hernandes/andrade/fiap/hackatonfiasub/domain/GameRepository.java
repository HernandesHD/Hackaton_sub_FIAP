package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByOwnerId(Integer ownerId);
}
