package com.hernandes.andrade.fiap.hackatonfiasub.usecase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangePreferenceTest {

    @Test
    void testEnumValues() {
        // Assert
        assertEquals("IN_PERSON", ExchangePreference.IN_PERSON.name());
        assertEquals("MAIL", ExchangePreference.MAIL.name());

        assertEquals(0, ExchangePreference.IN_PERSON.ordinal());
        assertEquals(1, ExchangePreference.MAIL.ordinal());
    }
}
