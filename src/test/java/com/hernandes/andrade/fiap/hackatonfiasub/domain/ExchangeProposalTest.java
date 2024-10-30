package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExchangeProposalTest {

    @Autowired
    private ExchangeProposalRepository exchangeProposalRepository;

    private User requester;
    private User owner;
    private Game requestedGame;
    private Game offeredGame;

    @BeforeEach
    void setUp() {
        // Configurar usuários e jogos, por exemplo, salvando-os em um repositório.
        requester = new User(); // Preencher com dados necessários
        owner = new User(); // Preencher com dados necessários
        requestedGame = new Game(); // Preencher com dados necessários
        offeredGame = new Game(); // Preencher com dados necessários
    }

    @Test
    void testExchangeProposalCreation() {
        ExchangeProposal proposal = ExchangeProposal.builder()
                .requester(requester)
                .owner(owner)
                .requestedGame(requestedGame)
                .offeredGame(offeredGame)
                .status("PENDING")
                .build();

        ExchangeProposal savedProposal = exchangeProposalRepository.save(proposal);

        assertThat(savedProposal).isNotNull();
        assertThat(savedProposal.getId()).isGreaterThan(0);
        assertThat(savedProposal.getStatus()).isEqualTo("PENDING");
        assertThat(savedProposal.getRequester()).isEqualTo(requester);
        assertThat(savedProposal.getOwner()).isEqualTo(owner);
    }
}