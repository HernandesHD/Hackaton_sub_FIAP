package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.ExchangeProposalDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.ExchangeService;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
@Tag(name = "Exchanges", description = "Operations related to exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @PostMapping("/propose")
    public ResponseEntity<ExchangeProposal> propose(@RequestBody ExchangeProposal exchangeProposal) {
        System.out.println("Received ExchangeProposal: " + exchangeProposal);

        // Verifique se o objeto é nulo
        if (exchangeProposal == null) {
            System.out.println("ExchangeProposal is null");
            return ResponseEntity.badRequest().body(null);
        }

        // Buscar as entidades no banco pelo ID para evitar objetos nulos
        User owner = userRepository.findById(exchangeProposal.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Game requestedGame = gameRepository.findById(exchangeProposal.getRequestedGame().getId())
                .orElseThrow(() -> new RuntimeException("Requested game not found"));
        Game offeredGame = gameRepository.findById(exchangeProposal.getOfferedGame().getId())
                .orElseThrow(() -> new RuntimeException("Offered game not found"));

        // Setando as entidades na proposta
        exchangeProposal.setOwner(owner);
        exchangeProposal.setRequestedGame(requestedGame);
        exchangeProposal.setOfferedGame(offeredGame);

        // Persistindo a proposta
        ExchangeProposal createdProposal = exchangeService.exchangePropose(exchangeProposal);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProposal);
    }

    /*@PostMapping("/propose")
    public ResponseEntity<ExchangeProposalDTO> propose(@RequestBody ExchangeProposalDTO exchangeProposalDTO) {

        System.out.println("Received DTO: " + exchangeProposalDTO);

        // Verifique se o DTO é nulo
        if (exchangeProposalDTO == null) {
            System.out.println("DTO is null");
            return ResponseEntity.badRequest().body(null);
        }

        // Verifique se o owner é nulo
        if (exchangeProposalDTO.getOwner() == null) {
            System.out.println("Owner is null");
            return ResponseEntity.badRequest().body(null);
        }

        ExchangeProposal proposal = ExchangeProposalDTO.toEntity(exchangeProposalDTO);

        // Buscar as entidades no banco pelo ID para evitar objetos nulos
        User owner = userRepository.findById(exchangeProposalDTO.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Game requestedGame = gameRepository.findById(exchangeProposalDTO.getRequestedGame().getId())
                .orElseThrow(() -> new RuntimeException("Requested game not found"));
        Game offeredGame = gameRepository.findById(exchangeProposalDTO.getOfferedGame().getId())
                .orElseThrow(() -> new RuntimeException("Offered game not found"));

        proposal.setOwner(owner);
        proposal.setRequestedGame(requestedGame);
        proposal.setOfferedGame(offeredGame);

        ExchangeProposal createdProposal = exchangeService.exchangePropose(proposal);
        ExchangeProposalDTO responseDTO = ExchangeProposalDTO.fromEntity(createdProposal);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }*/

    /*@PatchMapping("/accept/{id}")
    public ResponseEntity<ExchangeProposal> accept(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(exchangeService.acceptProposal(id));
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<ExchangeProposal> reject(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(exchangeService.rejectProposal(id));
    }*/

    @PatchMapping("/accept/{id}")
    public ResponseEntity<ExchangeProposal> acceptProposal(@PathVariable("id") Integer id,
                                                           @RequestParam Integer ownerId) {
        ExchangeProposal proposal = exchangeService.acceptProposal(id, ownerId);
        return ResponseEntity.ok(proposal);
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<ExchangeProposal> rejectProposal(@PathVariable("id") Integer id,
                                                           @RequestParam Integer ownerId) {
        ExchangeProposal proposal = exchangeService.rejectProposal(id, ownerId);
        return ResponseEntity.ok(proposal);
    }

}
