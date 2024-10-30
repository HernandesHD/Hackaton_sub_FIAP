package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void testGameCreation() {
        User owner = new User(); // Crie um usuário fictício ou um mock
        Game game = Game.builder()
                .title("Game Title")
                .description("Game Description")
                .platform("PC")
                .owner(owner)
                .build();

        assertThat(game).isNotNull();
        assertThat(game.getTitle()).isEqualTo("Game Title");
        assertThat(game.getDescription()).isEqualTo("Game Description");
        assertThat(game.getPlatform()).isEqualTo("PC");
        assertThat(game.getOwner()).isEqualTo(owner);
    }
}
