package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDTOTest {

    @Test
    void fromEntity_shouldConvertGameToDTO() {
        // Arrange
        Game game = new Game();
        game.setId(1);
        game.setTitle("Game Title");
        game.setDescription("Game Description");
        game.setPlatform("PC");

        // Act
        GameDTO dto = GameDTO.fromEntity(game);

        // Assert
        assertEquals(game.getId(), dto.getId());
        assertEquals(game.getTitle(), dto.getTitle());
        assertEquals(game.getDescription(), dto.getDescription());
        assertEquals(game.getPlatform(), dto.getPlatform());
    }

    @Test
    void toEntity_shouldConvertDTOToGame() {
        // Arrange
        GameDTO dto = new GameDTO();
        dto.setId(1);
        dto.setTitle("Game Title");
        dto.setDescription("Game Description");
        dto.setPlatform("PC");

        // Act
        Game game = GameDTO.toEntity(dto);

        // Assert
        assertEquals(dto.getId(), game.getId());
        assertEquals(dto.getTitle(), game.getTitle());
        assertEquals(dto.getDescription(), game.getDescription());
        assertEquals(dto.getPlatform(), game.getPlatform());
    }
}