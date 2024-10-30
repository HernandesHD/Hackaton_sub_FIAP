package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.GameRepository;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    private User owner;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setName("Test User");
        // Salvar o usuário no banco de dados (assumindo que você tem um repositório para isso)
        // userRepository.save(owner); // Exemplo
    }

    @Test
    void testSaveAndFindGame() {
        Game game = new Game();
        game.setTitle("Game Title");
        game.setDescription("Game Description");
        game.setPlatform("PC");
        game.setOwner(owner);

        gameRepository.save(game);

        Game foundGame = gameRepository.findById(game.getId()).orElse(null);
        assertThat(foundGame).isNotNull();
        assertThat(foundGame.getTitle()).isEqualTo("Game Title");
    }

    @Test
    void testFindByOwnerId() {
        // Crie e salve o proprietário antes de usar.
        owner = new User();
        owner.setName("Owner Name");
        owner.setEmail("owner@email.com");
        owner.setPassword("password123");
        owner.setPreference(ExchangeType.IN_PERSON);
        owner = userRepository.save(owner);

        Game game1 = new Game();
        game1.setTitle("Game Title 1");
        game1.setDescription("Game Description 1");
        game1.setPlatform("PC");
        game1.setOwner(owner); // O owner agora é persistido

        Game game2 = new Game();
        game2.setTitle("Game Title 2");
        game2.setDescription("Game Description 2");
        game2.setPlatform("PC");
        game2.setOwner(owner); // O owner agora é persistido

        // Salve os jogos
        gameRepository.save(game1);
        gameRepository.save(game2);

        List<Game> games = gameRepository.findByOwnerId(owner.getId());
        assertThat(games).hasSize(2);
        assertThat(games).contains(game1, game2);
    }

    @Test
    void testFindByOwnerIdWithNoGames() {
        List<Game> games = gameRepository.findByOwnerId(999); // ID que não existe
        assertThat(games).isEmpty();
    }
}
