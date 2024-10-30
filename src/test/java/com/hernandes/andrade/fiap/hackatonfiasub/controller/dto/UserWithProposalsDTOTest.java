package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.ExchangeProposalDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserWithProposalsDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserWithProposalsDTOTest {

    @Test
    void testFromEntity() {
        // Criando uma instância de User para teste
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setProposals(Collections.emptyList()); // Se necessário, adicione propostas

        UserWithProposalsDTO dto = UserWithProposalsDTO.fromEntity(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertTrue(dto.getProposals().isEmpty());
    }

    @Test
    void testFromUserAndProposals() {
        User user = new User();
        user.setId(2);
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");

        ExchangeProposalDTO proposalDTO = new ExchangeProposalDTO();
        proposalDTO.setId(1);
        proposalDTO.setStatus("Pending");

        UserWithProposalsDTO dto = UserWithProposalsDTO.fromUserAndProposals(user, Arrays.asList(proposalDTO));

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(1, dto.getProposals().size());
        assertEquals("Pending", dto.getProposals().get(0).getStatus());
    }
}