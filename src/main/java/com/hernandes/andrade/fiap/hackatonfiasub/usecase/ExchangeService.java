package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.EmailTemplateName;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposalRepository;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeProposalRepository exchangeProposalRepository;

    private final EmailService emailService;

    public ExchangeProposal exchangePropose(ExchangeProposal exchangeProposal) {
        exchangeProposal.setStatus("pending");

        ExchangeProposal savedProposal = exchangeProposalRepository.save(exchangeProposal);

        User owner = savedProposal.getOwner();
        if (owner == null || owner.getEmail() == null) {
            throw new IllegalArgumentException("Owner or owner email cannot be null.");
        }

        try {
            emailService.sendEmailExchangeNotification(
                    String.valueOf(owner.getId()),
                    owner.getEmail(),
                    owner.getName(),
                    EmailTemplateName.EXCHANGE_NOTIFICATION,
                    savedProposal.getOfferedGame().getTitle(),
                    savedProposal.getOfferedGame().getDescription(),
                    savedProposal.getOfferedGame().getPlatform(),
                    savedProposal.getRequestedGame().getTitle(),
                    savedProposal.getRequestedGame().getDescription(),
                    savedProposal.getRequestedGame().getPlatform(),
                    "EXCHANGE PROPOSE"
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }

        return savedProposal;
    }

    public ExchangeProposal acceptProposal(Integer proposalId, Integer ownerId) {
        ExchangeProposal proposal = exchangeProposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // Verifica se o owner é o mesmo que está avaliando
        if (!proposal.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Only the owner can accept this proposal.");
        }

        // Atualiza o status da proposta para 'ACCEPTED'
        proposal.setStatus("ACCEPTED");
        return exchangeProposalRepository.save(proposal);
    }

    public ExchangeProposal rejectProposal(Integer proposalId, Integer ownerId) {
        ExchangeProposal proposal = exchangeProposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // Verifica se o owner é o mesmo que está avaliando
        if (!proposal.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Only the owner can reject this proposal.");
        }

        // Atualiza o status da proposta para 'REJECTED'
        proposal.setStatus("REJECTED");
        return exchangeProposalRepository.save(proposal);
    }

    /*public ExchangeProposal acceptProposal(Integer proposalId) {
        return exchangeProposalRepository.findById(proposalId).map(proposal -> {
            proposal.setStatus("accepted");
            return exchangeProposalRepository.save(proposal);
        }).orElseThrow(() -> new RuntimeException("Could not find proposal with id: " + proposalId));
    }

    public ExchangeProposal rejectProposal(Integer proposalId) {
        return exchangeProposalRepository.findById(proposalId).map(proposal -> {
            proposal.setStatus("rejected");
            return exchangeProposalRepository.save(proposal);
        }).orElseThrow(() -> new RuntimeException("Could not find proposal with id: " + proposalId));
    }*/

    public List<ExchangeProposal> findByRequesterId(Integer requesterId) {
        return exchangeProposalRepository.findByRequester_Id(requesterId);
    }
}
