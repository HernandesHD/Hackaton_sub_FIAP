package com.hernandes.andrade.fiap.hackatonfiasub.exception;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionResponseTest {

    @Test
    void testDefaultConstructor() {
        ExceptionResponse response = new ExceptionResponse();
        assertThat(response.getBusinessErrorCode()).isNull();
        assertThat(response.getBusinessErrorDescription()).isNull();
        assertThat(response.getError()).isNull();
        assertThat(response.getValidationErrors()).isNull();
        assertThat(response.getErros()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Set<String> validationErrors = new HashSet<>();
        validationErrors.add("error1");
        validationErrors.add("error2");

        Map<String, String> erros = new HashMap<>();
        erros.put("field1", "error message");

        ExceptionResponse response = new ExceptionResponse(400, "Bad Request", "error", validationErrors, erros);

        assertThat(response.getBusinessErrorCode()).isEqualTo(400);
        assertThat(response.getBusinessErrorDescription()).isEqualTo("Bad Request");
        assertThat(response.getError()).isEqualTo("error");
        assertThat(response.getValidationErrors()).containsExactlyInAnyOrder("error1", "error2");
        assertThat(response.getErros()).containsEntry("field1", "error message");
    }

    @Test
    void testBuilder() {
        Set<String> validationErrors = new HashSet<>();
        validationErrors.add("error1");

        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorCode(500)
                .businessErrorDescription("Internal Server Error")
                .error("error")
                .validationErrors(validationErrors)
                .build();

        assertThat(response.getBusinessErrorCode()).isEqualTo(500);
        assertThat(response.getBusinessErrorDescription()).isEqualTo("Internal Server Error");
        assertThat(response.getError()).isEqualTo("error");
        assertThat(response.getValidationErrors()).containsExactly("error1");
    }

    // Teste para garantir a inclusão do JsonInclude
    @Test
    void testJsonInclude() {
        ExceptionResponse response = new ExceptionResponse();
        assertThat(response).isNotNull();
        assertThat(response.getBusinessErrorCode()).isNull();
        assertThat(response.getBusinessErrorDescription()).isNull();
        assertThat(response.getError()).isNull();
        assertThat(response.getValidationErrors()).isNull();
        assertThat(response.getErros()).isNull();
    }
}
