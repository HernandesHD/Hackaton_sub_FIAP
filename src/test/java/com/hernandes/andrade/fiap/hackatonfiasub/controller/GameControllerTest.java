package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.GameDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddGameSuccessfully() {
        Game game = new Game(); // Mock or create a Game instance
        game.setId(1); // Set an ID for testing

        when(gameService.addGame(game)).thenReturn(game);

        ResponseEntity<GameDTO> response = gameController.addGame(game);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(game.getId(), response.getBody().getId());
        verify(gameService).addGame(game);
    }

    @Test
    void shouldGetGameSuccessfully() {
        Game game = new Game(); // Mock or create a Game instance
        game.setId(1);

        when(gameService.getGameById(1)).thenReturn(Optional.of(game));

        ResponseEntity<GameDTO> response = gameController.getGame(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(game.getId(), response.getBody().getId());
        verify(gameService).getGameById(1);
    }

    @Test
    void shouldReturnNotFoundWhenGameDoesNotExist() {
        when(gameService.getGameById(1)).thenReturn(Optional.empty());

        ResponseEntity<GameDTO> response = gameController.getGame(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(gameService).getGameById(1);
    }

    @Test
    void shouldUpdateGameSuccessfully() {
        Game game = new Game(); // Mock or create a Game instance
        game.setId(1);

        when(gameService.updateGame(1, game)).thenReturn(game);

        ResponseEntity<Game> response = gameController.updateGame(1, game);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(game.getId(), response.getBody().getId());
        verify(gameService).updateGame(1, game);
    }

    @Test
    void shouldDeleteGameSuccessfully() {
        doNothing().when(gameService).deleteGameById(1);

        ResponseEntity<Void> response = gameController.deleteGame(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(gameService).deleteGameById(1);
    }
}