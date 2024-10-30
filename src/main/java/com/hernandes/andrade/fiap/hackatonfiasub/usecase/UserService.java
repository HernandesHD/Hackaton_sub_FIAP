package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.ExchangeProposalDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.GameDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.dto.UserRoleDTO;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.ExchangeType;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.User;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::fromEntity);
    }

    /*public Optional<UserDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity);
    }*/
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

}
