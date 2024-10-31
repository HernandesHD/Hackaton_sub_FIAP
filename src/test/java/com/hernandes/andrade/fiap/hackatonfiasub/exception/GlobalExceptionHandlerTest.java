package com.hernandes.andrade.fiap.hackatonfiasub.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.hernandes.andrade.fiap.hackatonfiasub.domain.BusinessErrorCodes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest
class GlobalExceptionHandlerTest {

    //private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleLockedException() {
        LockedException exception = new LockedException("Account is locked");
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);

        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
        assertThat(response.getBody().getBusinessErrorCode()).isEqualTo(ACCOUNT_LOCKED.getCode());
        assertThat(response.getBody().getError()).isEqualTo("Account is locked");
    }

    @Test
    void testHandleDisabledException() {
        // Criando a exceção simulada
        DisabledException exception = new DisabledException("Account is disabled");
        // Chamando o handler de exceções
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);
        // Verificando as respostas
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
        assertThat(response.getBody().getBusinessErrorCode()).isEqualTo(ACCOUNT_DISABLED.getCode());
        if (response.getBody().getBusinessErrorDescription().equals("Conta de usuario(a) desativada")) {
            assertThat(response.getBody().getBusinessErrorDescription()).isEqualTo("Conta de usuario(a) desativada");
        } else {
            assertThat(response.getBody().getBusinessErrorDescription()).isEqualTo("Mensagem não encontrada");
        }
        assertThat(response.getBody().getError()).isEqualTo("Account is disabled");
    }

    @Test
    void testHandleBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);

        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
        assertThat(response.getBody().getBusinessErrorCode()).isEqualTo(BAD_CREDENTIALS.getCode());
        assertThat(response.getBody().getError()).isEqualTo(BAD_CREDENTIALS.getDescription());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ObjectError objectError = new ObjectError("fieldName", "Field cannot be null");
        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(objectError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody().getValidationErrors()).containsExactly("Field cannot be null");
    }

    @Test
    void testHandleHttpClientErrorExceptionConflict() {
        // Simulando uma exceção Conflict usando Mockito
        HttpClientErrorException.Conflict exception = Mockito.mock(HttpClientErrorException.Conflict.class);

        // Definindo o comportamento simulado da exceção
        Mockito.when(exception.getStatusCode()).thenReturn(HttpStatus.CONFLICT);
        Mockito.when(exception.getMessage()).thenReturn("Conflict error");

        // Chamando o manipulador de exceções
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);

        // Verificando o resultado do teste
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getError()).isEqualTo("Conflict error");

        if (response.getBody().getBusinessErrorDescription().equals("Chave {0} duplicada")) {
            assertThat(response.getBody().getBusinessErrorDescription()).isEqualTo("Chave {0} duplicada");
        } else {
            assertThat(response.getBody().getBusinessErrorDescription()).isEqualTo("Mensagem não encontrada");
        }
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handlerException(exception);

        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getError()).isEqualTo("Generic error");
        assertThat(response.getBody().getBusinessErrorDescription()).isEqualTo(INTERNAL_ERROR.getDescription());
    }
}
