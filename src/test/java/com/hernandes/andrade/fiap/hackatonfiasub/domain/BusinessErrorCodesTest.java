package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessErrorCodesTest {

    @Test
    void testEnumValues() {
        assertEquals(0, BusinessErrorCodes.NO_CODE.getCode());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, BusinessErrorCodes.NO_CODE.getHttpStatus());
        assertEquals("Sem código", BusinessErrorCodes.NO_CODE.getDescription());

        assertEquals(300, BusinessErrorCodes.INCORRECT_CURRENT_PASSWORD.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, BusinessErrorCodes.INCORRECT_CURRENT_PASSWORD.getHttpStatus());
        assertEquals("Senha incorreta", BusinessErrorCodes.INCORRECT_CURRENT_PASSWORD.getDescription());
    }

    @Test
    void testGetDescription() {
        BusinessErrorCodes.setMessageSource(new ResourceBundleMessageSource());
        String description = BusinessErrorCodes.INCORRECT_CURRENT_PASSWORD.getDescription();
        assertNotNull(description);
    }
}
