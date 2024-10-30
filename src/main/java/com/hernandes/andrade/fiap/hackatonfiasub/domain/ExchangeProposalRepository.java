package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeProposalRepository extends JpaRepository<ExchangeProposal, Integer> {
    List<ExchangeProposal> findByOwnerId(Integer id);

    List<ExchangeProposal> findByRequester_Id(Integer requesterId);
}
