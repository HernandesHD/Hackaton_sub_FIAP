package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.GameDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Tag(name = "Games", description = "Operations related to game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/add")
    public ResponseEntity<GameDTO> addGame(@RequestBody Game game) {
        Game createdGame = gameService.addGame(game);
        GameDTO gameDTO = GameDTO.fromEntity(createdGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable Integer id) {
        return gameService.getGameById(id)
                .map(game -> ResponseEntity.ok(GameDTO.fromEntity(game))) // Converte Game para GameDTO
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer id, @RequestBody Game game) {
        return ResponseEntity.ok(gameService.updateGame(id, game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
        return ResponseEntity.noContent().build();
    }

}
