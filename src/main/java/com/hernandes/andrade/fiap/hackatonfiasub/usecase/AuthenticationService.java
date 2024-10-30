package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import com.hernandes.andrade.fiap.hackatonfiasub.config.JwtService;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.LoginRequest;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.LoginResponse;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.RegistrationRequest;
import com.hernandes.andrade.fiap.hackatonfiasub.controller.ResetPasswordRequest;
import com.hernandes.andrade.fiap.hackatonfiasub.domain.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Value("${application.mailing.frontend.reset-password-url}")
    private String resetPasswordUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = userRoleRepository.findByName("USER")
                //todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .preference(request.getExchangeType())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocket(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        // send email
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    user.getName(),
                    EmailTemplateName.ACTIVATE_ACCOUNT,
                    activationUrl,
                    newToken,
                    "Activate your account"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateAndSaveActivationToken(User user) {
        // generate token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        // save token
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getName());
        var token = jwtService.generateToken(claims, user);
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("exception.invalidToken"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("exception.experidToken");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("exception.userNotFound"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    // Método forgotPassword para gerar e enviar o token de recuperação
    public void forgotPassword(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("exception.userNotFound"));

        var newToken = generateAndSaveResetToken(user);

        try {
            emailService.sendEmail(
                    user.getEmail(),
                    user.getName(),
                    EmailTemplateName.RESET_PASSWORD,
                    resetPasswordUrl,
                    newToken,
                    "Reset your password"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateAndSaveResetToken(User user) {
        String generatedToken = generateActivationCode(6); // Gera um token de 6 dígitos
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15)) // Expira em 15 minutos
                .user(user)
                .build();

        tokenRepository.save(token); // Salva o token no repositório
        return generatedToken;
    }

    // Método para redefinição de senha
    @Transactional
    public void resetPassword(String token, ResetPasswordRequest resetPasswordRequest) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("exception.invalidToken"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            throw new RuntimeException("exception.expiredToken");
        }

        var user = savedToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
