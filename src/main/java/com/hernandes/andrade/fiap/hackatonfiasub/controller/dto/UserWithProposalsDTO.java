package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserWithProposalsDTO {
    private Integer id;
    private String name;
    private String email;
    // Adicione outros campos necessários
    private List<ExchangeProposalDTO> proposals;

    public static UserWithProposalsDTO fromEntity(User user) {
        UserWithProposalsDTO dto = new UserWithProposalsDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProposals(user.getProposals()
                .stream()
                .map(ExchangeProposalDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }

    public static UserWithProposalsDTO fromUserAndProposals(User user, List<ExchangeProposalDTO> proposals) {
        UserWithProposalsDTO dto = new UserWithProposalsDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProposals(proposals);
        return dto;
    }
}
