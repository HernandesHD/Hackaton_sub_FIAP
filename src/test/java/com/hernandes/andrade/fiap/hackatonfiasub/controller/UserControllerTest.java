package com.hernandes.andrade.fiap.hackatonfiasub.controller;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.ExchangeProposalDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserWithProposalsDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeProposal;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.ExchangeService;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ExchangeService exchangeService;

    /*@Test
    void getUserById_shouldReturnUserWithProposals() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        ExchangeProposal proposal = new ExchangeProposal();
        proposal.setId(1);

        // Mocking the services
       // when(userService.findById(1)).thenReturn(Optional.of(user));
        //when(exchangeService.findByRequesterId(1)).thenReturn(List.of(proposal));

        // Act
        ResponseEntity<UserWithProposalsDTO> response = userController.getUserById(1);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        UserWithProposalsDTO userWithProposalsDTO = response.getBody();
        assertNotNull(userWithProposalsDTO, "Response body should not be null");
        assertEquals("Test User", userWithProposalsDTO.getName());
        assertEquals(1, userWithProposalsDTO.getProposals().size());

        // Verify interactions
        verify(userService).findById(1);
        verify(exchangeService).findByRequesterId(1);
    }*/

    @Test
    void getUserById_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userService.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.getUserById(1);
        });
        assertEquals("User not found", exception.getMessage());

        // Verify interactions
        verify(userService).findById(1);
        verify(exchangeService, never()).findByRequesterId(anyInt()); // Ensures this is not called
    }
}