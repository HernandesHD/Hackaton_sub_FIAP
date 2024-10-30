package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;

import com.hernandes.andrade.fiap.hackatonfiasub.usecase.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExchangeControllerTest {

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ExchangeController exchangeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProposeExchangeSuccessfully() {
        ExchangeProposal exchangeProposal = new ExchangeProposal();
        // Populate exchangeProposal with test data

        User user = new User(); // Mock or create a User instance
        user.setId(1);
        exchangeProposal.setOwner(user);

        Game requestedGame = new Game(); // Mock or create a Game instance
        requestedGame.setId(2);
        exchangeProposal.setRequestedGame(requestedGame);

        Game offeredGame = new Game(); // Mock or create a Game instance
        offeredGame.setId(3);
        exchangeProposal.setOfferedGame(offeredGame);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(gameRepository.findById(2)).thenReturn(Optional.of(requestedGame));
        when(gameRepository.findById(3)).thenReturn(Optional.of(offeredGame));
        when(exchangeService.exchangePropose(exchangeProposal)).thenReturn(exchangeProposal);

        ResponseEntity<ExchangeProposal> response = exchangeController.propose(exchangeProposal);

        verify(exchangeService).exchangePropose(exchangeProposal);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(exchangeProposal, response.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenExchangeProposalIsNull() {
        ResponseEntity<ExchangeProposal> response = exchangeController.propose(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void shouldAcceptProposalSuccessfully() {
        ExchangeProposal proposal = new ExchangeProposal(); // Mock or create a proposal instance
        when(exchangeService.acceptProposal(1, 1)).thenReturn(proposal);

        ResponseEntity<ExchangeProposal> response = exchangeController.acceptProposal(1, 1);

        verify(exchangeService).acceptProposal(1, 1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(proposal, response.getBody());
    }

    @Test
    void shouldRejectProposalSuccessfully() {
        ExchangeProposal proposal = new ExchangeProposal(); // Mock or create a proposal instance
        when(exchangeService.rejectProposal(1, 1)).thenReturn(proposal);

        ResponseEntity<ExchangeProposal> response = exchangeController.rejectProposal(1, 1);

        verify(exchangeService).rejectProposal(1, 1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(proposal, response.getBody());
    }
}