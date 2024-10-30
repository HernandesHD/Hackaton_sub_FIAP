package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}

/*@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private boolean accountLocket;
    private boolean enabled;
    private ExchangeType preference;
    private List<UserRoleDTO> roles;
    private List<GameDTO> games;
    private List<ExchangeProposalDTO> proposals;
    //private List<Integer> gameIds; // Armazena apenas os IDs dos jogos
    //private List<Integer> proposalIds;

    // Método estático para converter a entidade User para UserDTO
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isAccountLocket(),
                user.isEnabled(),
                user.getPreference(),
                user.getRoles().stream().map(UserRoleDTO::fromEntity).collect(Collectors.toList()),
                user.getGames().stream().map(GameDTO::fromEntity).collect(Collectors.toList()),
                user.getProposals().stream().map(ExchangeProposalDTO::fromEntity).collect(Collectors.toList())
                //user.getGames().stream().map(Game::getId).collect(Collectors.toList()),
                //user.getProposals().stream().map(ExchangeProposal::getId).collect(Collectors.toList())
        );
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .accountLocket(dto.isAccountLocket())
                .enabled(dto.isEnabled())
                .preference(dto.getPreference())
                .build();
    }

    /*public static User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getId(), // Caso o ID não seja necessário, você pode omitir
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.isAccountLocket(),
                userDTO.isEnabled(),
                userDTO.getPreference(),
                userDTO.getRoles().stream().map(UserRoleDTO::toEntity).collect(Collectors.toList()), // Converter UserRoleDTO para UserRole
                userDTO.getGames().stream().map(GameDTO::toEntity).collect(Collectors.toList()), // Converter GameDTO para Game
                userDTO.getProposals().stream().map(ExchangeProposalDTO::toEntity).collect(Collectors.toList()) // Converter ExchangeProposalDTO para ExchangeProposal
        );
    }*/

/*}*/
