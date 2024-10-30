package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposalRepository;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
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
class ExchangeProposalRepositoryTest {

    @Autowired
    private ExchangeProposalRepository exchangeProposalRepository;

    private User owner;
    private User requester;
    private Game requestedGame;
    private Game offeredGame;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        // Criar e salvar usuários
        owner = User.builder()
                .name("teste01")
                .email("teste@email.com")
                .password("12345678")
                .preference(ExchangeType.IN_PERSON)
                .build();
        requester = User.builder()
                .name("teste02")
                .email("teste2@email.com")
                .password("12345678")
                .preference(ExchangeType.IN_PERSON)
                .build();

        userRepository.save(owner);
        userRepository.save(requester);

        // Criar e salvar jogos
        requestedGame = Game.builder()
                .title("Game 01")
                .description("Desc 01")
                .platform("PC")
                .build();
        offeredGame = Game.builder()
                .title("Game 02")
                .description("Desc 02")
                .platform("XBOX")
                .build();

        gameRepository.save(requestedGame);
        gameRepository.save(offeredGame);

        // Criar e salvar propostas de intercâmbio
        ExchangeProposal proposal1 = ExchangeProposal.builder()
                .owner(owner)
                .requester(requester)
                .requestedGame(requestedGame)
                .offeredGame(offeredGame)
                .status("PENDING")
                .build();

        exchangeProposalRepository.save(proposal1);

        ExchangeProposal proposal2 = ExchangeProposal.builder()
                .owner(owner)
                .requester(requester)
                .requestedGame(requestedGame)
                .offeredGame(offeredGame)
                .status("COMPLETED")
                .build();

        exchangeProposalRepository.save(proposal2);
    }

    @Test
    void testFindByOwnerId() {
        List<ExchangeProposal> proposals = exchangeProposalRepository.findByOwnerId(owner.getId());
        assertThat(proposals).isNotEmpty();
        assertThat(proposals).hasSize(2); // Verifica se as duas propostas foram retornadas
    }

    @Test
    void testFindByRequesterId() {
        List<ExchangeProposal> proposals = exchangeProposalRepository.findByRequester_Id(requester.getId());
        assertThat(proposals).isNotEmpty();
        assertThat(proposals).hasSize(2); // Verifica se as duas propostas foram retornadas
    }
}
