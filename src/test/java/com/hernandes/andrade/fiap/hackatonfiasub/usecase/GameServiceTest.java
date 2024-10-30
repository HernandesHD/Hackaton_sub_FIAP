package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    public GameServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddGame() {
        Game game = new Game();
        game.setTitle("Game Title");
        game.setDescription("Game Description");
        game.setPlatform("PS5");

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game result = gameService.addGame(game);
        assertEquals("Game Title", result.getTitle());
    }

    @Test
    void testGetGameById_Success() {
        Game game = new Game();
        game.setId(1);
        game.setTitle("Game Title");

        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.getGameById(1);
        assertTrue(result.isPresent());
        assertEquals("Game Title", result.get().getTitle());
    }

    @Test
    void testGetGameById_NotFound() {
        when(gameRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Game> result = gameService.getGameById(1);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateGame_Success() {
        Game existingGame = new Game();
        existingGame.setId(1);
        existingGame.setTitle("Old Title");

        Game updatedGame = new Game();
        updatedGame.setTitle("New Title");
        updatedGame.setDescription("New Description");
        updatedGame.setPlatform("PC");

        when(gameRepository.findById(1)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(any(Game.class))).thenReturn(existingGame);

        Game result = gameService.updateGame(1, updatedGame);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testUpdateGame_NotFound() {
        Game updatedGame = new Game();
        updatedGame.setTitle("New Title");

        when(gameRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.updateGame(1, updatedGame);
        });

        assertEquals("Game not found", exception.getMessage());
    }

    @Test
    void testDeleteGameById() {
        doNothing().when(gameRepository).deleteById(1);

        assertDoesNotThrow(() -> gameService.deleteGameById(1));
        verify(gameRepository, times(1)).deleteById(1);
    }
}
