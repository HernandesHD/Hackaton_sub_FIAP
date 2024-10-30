package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.ExchangeProposalDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserWithProposalsDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.ExchangeService;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to user")
public class UserController {

    private final UserService userService;
    private final ExchangeService exchangeService;

    @GetMapping("/{id}")
    public ResponseEntity<UserWithProposalsDTO> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ExchangeProposal> proposals = exchangeService.findByRequesterId(id);
        List<ExchangeProposalDTO> proposalDTOs = proposals.stream()
                .map(ExchangeProposalDTO::fromEntity)
                .collect(Collectors.toList());

        UserWithProposalsDTO userWithProposalsDTO = UserWithProposalsDTO.fromUserAndProposals(user, proposalDTOs);

        return ResponseEntity.ok(userWithProposalsDTO);
        /*return userService.findById(id)
                .map(UserWithProposalsDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());*/
    }
}
