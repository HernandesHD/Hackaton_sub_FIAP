package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleDTOTest {

    @Test
    void fromEntity_shouldConvertUserRoleToDTO() {
        // Arrange
        UserRole userRole = new UserRole();
        userRole.setName("ADMIN");

        // Act
        UserRoleDTO dto = UserRoleDTO.fromEntity(userRole);

        // Assert
        assertEquals(userRole.getName(), dto.getName());
    }

    @Test
    void toEntity_shouldConvertDTOToUserRole() {
        // Arrange
        UserRoleDTO dto = new UserRoleDTO();
        dto.setName("ADMIN");

        // Act
        UserRole userRole = UserRoleDTO.toEntity(dto);

        // Assert
        assertEquals(dto.getName(), userRole.getName());
    }
}