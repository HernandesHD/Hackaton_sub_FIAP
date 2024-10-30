package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.Game;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.stream.Collectors;

@Data
public class GameDTO {
    private Integer id;
    private String title; // Supondo que "title" é um campo do jogo
    private String description;
    private String platform;// Outros detalhes do jogo, se necessário

    public static GameDTO fromEntity(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPlatform(game.getPlatform());
        return dto;
    }

    public static Game toEntity(GameDTO dto) {
        return Game.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .platform(dto.getPlatform())
                .build();
    }
}

/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private Integer id;
    private String title;
    private String description;
    private String platform;
    //private UserDTO owner;

    public static GameDTO fromEntity(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getPlatform()
                //UserDTO.fromEntity(game.getOwner())
        );
    }

    public static Game toEntity(GameDTO dto) {
        return Game.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .platform(dto.getPlatform())
                .build();
    }

    /*public static Game toEntity(GameDTO gameDTO) {
        return new Game(
                gameDTO.getId(), // Caso o ID não seja necessário no novo objeto, você pode ignorar
                gameDTO.getTitle(),
                gameDTO.getDescription(),
                gameDTO.getPlatform(),
                UserDTO.toEntity(gameDTO.getOwner()) // Certifique-se de que você tenha um método toEntity para o UserDTO
        );
    }*/
//}
