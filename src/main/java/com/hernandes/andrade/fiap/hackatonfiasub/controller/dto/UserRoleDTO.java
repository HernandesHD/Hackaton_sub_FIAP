package com.hernandes.andrade.fiap.hackatonfiasub.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private String name;

    public static UserRoleDTO fromEntity(UserRole userRole) {
        return new UserRoleDTO(
          userRole.getName()
        );
    }

    public static UserRole toEntity(UserRoleDTO userRoleDTO) {
        return new UserRole(
                userRoleDTO.getName()
        );
    }
}
