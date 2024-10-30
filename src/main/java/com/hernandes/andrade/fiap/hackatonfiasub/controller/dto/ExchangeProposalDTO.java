    package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
    import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
    import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import lombok.*;

    @Data
    public class ExchangeProposalDTO {
        private Integer id;
        private UserDTO requester; // Usando UserDTO
        private UserDTO owner; // Usando UserDTO
        private GameDTO requestedGame; // Usando GameDTO
        private GameDTO offeredGame; // Usando GameDTO
        private String status;

        public static ExchangeProposalDTO fromEntity(ExchangeProposal proposal) {
            ExchangeProposalDTO dto = new ExchangeProposalDTO();
            dto.setId(proposal.getId());
            dto.setRequester(UserDTO.fromEntity(proposal.getRequester())); // Usando UserDTO
            dto.setOwner(UserDTO.fromEntity(proposal.getOwner())); // Usando UserDTO
            dto.setRequestedGame(GameDTO.fromEntity(proposal.getRequestedGame())); // Usando GameDTO
            dto.setOfferedGame(GameDTO.fromEntity(proposal.getOfferedGame())); // Usando GameDTO
            dto.setStatus(proposal.getStatus());
            return dto;
        }

        public static ExchangeProposal toEntity(ExchangeProposalDTO dto) {
            return ExchangeProposal.builder()
                    .id(dto.getId())
                    .owner(UserDTO.toEntity(dto.getOwner()))
                    .requestedGame(GameDTO.toEntity(dto.getRequestedGame()))
                    .offeredGame(GameDTO.toEntity(dto.getOfferedGame()))
                    .status(dto.getStatus())
                    .build();
        }
    }

    /*@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ExchangeProposalDTO {
        private Integer id;
        private String status;
        //@JsonIgnore
        //private UserDTO requester;
        //@JsonIgnore
        private UserDTO owner;
        private GameDTO requestedGame;
        private GameDTO offeredGame;

        public static ExchangeProposalDTO fromEntity(ExchangeProposal exchangeProposal) {
            return new ExchangeProposalDTO(
                    exchangeProposal.getId(),
                    exchangeProposal.getStatus(),
                    //UserDTO.fromEntity(exchangeProposal.getRequester()),
                    UserDTO.fromEntity(exchangeProposal.getOwner()),
                    GameDTO.fromEntity(exchangeProposal.getRequestedGame()),
                    GameDTO.fromEntity(exchangeProposal.getOfferedGame())
            );
        }

        public static ExchangeProposal toEntity(ExchangeProposalDTO dto) {
            return ExchangeProposal.builder()
                    .id(dto.getId())
                    .owner(UserDTO.toEntity(dto.getOwner()))
                    .requestedGame(GameDTO.toEntity(dto.getRequestedGame()))
                    .offeredGame(GameDTO.toEntity(dto.getOfferedGame()))
                    .status(dto.getStatus())
                    .build();
        }

        /*public static ExchangeProposal toEntity(ExchangeProposalDTO exchangeProposalDTO) {
            return new ExchangeProposal(
                    exchangeProposalDTO.getId(), // Use null se o ID for gerado automaticamente
                    UserDTO.toEntity(exchangeProposalDTO.getRequester()), // Converte UserDTO para User
                    UserDTO.toEntity(exchangeProposalDTO.getOwner()), // Converte UserDTO para User
                    GameDTO.toEntity(exchangeProposalDTO.getRequestedGame()), // Converte GameDTO para Game
                    GameDTO.toEntity(exchangeProposalDTO.getOfferedGame()), // Converte GameDTO para Game
                    exchangeProposalDTO.getStatus()
            );
        }*/

    /*}*/