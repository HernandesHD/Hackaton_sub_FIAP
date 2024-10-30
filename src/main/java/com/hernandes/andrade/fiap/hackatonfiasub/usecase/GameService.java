package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> getGameById(Integer id) {
        return gameRepository.findById(id);
    }

    public Game updateGame(Integer id, Game updatedGame) {
        return gameRepository.findById(id).map(game -> {
            game.setTitle(updatedGame.getTitle());
            game.setDescription(updatedGame.getDescription());
            game.setPlatform(updatedGame.getPlatform());
            return gameRepository.save(game);
        }).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public void deleteGameById(Integer id) {
        gameRepository.deleteById(id);
    }

}
