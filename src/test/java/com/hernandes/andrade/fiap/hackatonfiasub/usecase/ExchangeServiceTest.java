package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

class ExchangeServiceTest {

    @Mock
    private ExchangeProposalRepository exchangeProposalRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExchangePropose_Success() throws IOException {
        // Arrange
        Game offeredGame = new Game();
        offeredGame.setTitle("Offered Game Title");
        offeredGame.setDescription("Offered Game Description");
        offeredGame.setPlatform("PS5");

        Game requestedGame = new Game();
        requestedGame.setTitle("Requested Game Title");
        requestedGame.setDescription("Requested Game Description");
        requestedGame.setPlatform("Xbox");

        ExchangeProposal proposal = new ExchangeProposal();

        // Criar e configurar o proprietário
        User owner = new User();
        owner.setId(1); // Certifique-se de que o ID está definido
        owner.setName("Test");
        owner.setEmail("test@example.com");
        owner.setPassword("password123");
        owner.setPreference(ExchangeType.IN_PERSON); // Certifique-se de usar o tipo correto aqui
        owner.setRoles(Collections.singletonList(new UserRole("USER")));

        // Definindo o proprietário da proposta
        proposal.setOwner(owner);
        proposal.setOfferedGame(offeredGame);
        proposal.setRequestedGame(requestedGame);

        when(exchangeProposalRepository.save(any(ExchangeProposal.class))).thenReturn(proposal);

        // Act
        ExchangeProposal result = exchangeService.exchangePropose(proposal);

        // Assert
        assertEquals("pending", result.getStatus());
        verify(emailService).sendEmailExchangeNotification(
                eq("1"), // ID do proprietário
                eq("test@example.com"), // Email do proprietário
                eq("Test"), // Nome do proprietário
                any(),
                eq(offeredGame.getTitle()),
                eq(offeredGame.getDescription()),
                eq(offeredGame.getPlatform()),
                eq(requestedGame.getTitle()),
                eq(requestedGame.getDescription()),
                eq(requestedGame.getPlatform()),
                any()
        );
    }

    @Test
    void testExchangePropose_OwnerEmailNull() {
        // Arrange
        ExchangeProposal proposal = new ExchangeProposal();
        proposal.setOwner(new User()); // Owner without email
        when(exchangeProposalRepository.save(any(ExchangeProposal.class))).thenReturn(proposal);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> exchangeService.exchangePropose(proposal));
    }

    @Test
    void testAcceptProposal_Success() {
        // Arrange
        ExchangeProposal proposal = new ExchangeProposal();
        User owner = new User();
        owner.setId(1);
        proposal.setOwner(owner);
        proposal.setStatus("pending");
        when(exchangeProposalRepository.findById(1)).thenReturn(Optional.of(proposal));
        when(exchangeProposalRepository.save(any(ExchangeProposal.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        ExchangeProposal result = exchangeService.acceptProposal(1, 1);
        // Assert
        assertEquals("ACCEPTED", result.getStatus());
    }

    @Test
    void testAcceptProposal_ProposalNotFound() {
        when(exchangeProposalRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> exchangeService.acceptProposal(1, 1));
    }

    @Test
    void testRejectProposal_NotOwner() {
        ExchangeProposal proposal = new ExchangeProposal();
        proposal.setOwner(new User());
        proposal.getOwner().setId(1);
        when(exchangeProposalRepository.findById(1)).thenReturn(Optional.of(proposal));
        assertThrows(RuntimeException.class, () -> exchangeService.rejectProposal(1, 2));
    }
}
