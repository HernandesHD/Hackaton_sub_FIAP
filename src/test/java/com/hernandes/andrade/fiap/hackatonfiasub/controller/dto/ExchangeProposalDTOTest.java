package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeProposalDTOTest {

    @Test
    void fromEntity_shouldConvertExchangeProposalToDTO() {
        // Arrange
        User requester = new User();
        requester.setId(1);
        requester.setName("Requester User");

        User owner = new User();
        owner.setId(2);
        owner.setName("Owner User");

        Game requestedGame = new Game(); // Preencha com dados reais conforme necessário
        requestedGame.setId(1);
        requestedGame.setTitle("Requested Game");

        Game offeredGame = new Game(); // Preencha com dados reais conforme necessário
        offeredGame.setId(2);
        offeredGame.setTitle("Offered Game");

        ExchangeProposal proposal = new ExchangeProposal();
        proposal.setId(1);
        proposal.setRequester(requester);
        proposal.setOwner(owner);
        proposal.setRequestedGame(requestedGame);
        proposal.setOfferedGame(offeredGame);
        proposal.setStatus("Pending");

        // Act
        ExchangeProposalDTO dto = ExchangeProposalDTO.fromEntity(proposal);

        // Assert
        assertEquals(proposal.getId(), dto.getId());
        assertEquals("Pending", dto.getStatus());
        assertNotNull(dto.getRequester());
        assertNotNull(dto.getOwner());
        assertNotNull(dto.getRequestedGame());
        assertNotNull(dto.getOfferedGame());
        assertEquals(requester.getId(), dto.getRequester().getId());
        assertEquals(owner.getId(), dto.getOwner().getId());
        assertEquals(requestedGame.getId(), dto.getRequestedGame().getId());
        assertEquals(offeredGame.getId(), dto.getOfferedGame().getId());
    }

}