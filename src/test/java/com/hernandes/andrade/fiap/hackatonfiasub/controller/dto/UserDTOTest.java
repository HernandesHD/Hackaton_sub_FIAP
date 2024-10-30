package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void fromEntity_shouldConvertUserToDTO() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        // Act
        UserDTO dto = UserDTO.fromEntity(user);

        // Assert
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    void toEntity_shouldConvertDTOToUser() {
        // Arrange
        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");

        // Act
        User user = UserDTO.toEntity(dto);

        // Assert
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
    }
}