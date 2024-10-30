package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ExchangeTypeTest {

    @Test
    void testExchangeTypeValues() {
        // Verifica se todos os valores do enum estão corretos
        assertThat(ExchangeType.values()).containsExactlyInAnyOrder(ExchangeType.IN_PERSON, ExchangeType.DELIVERY);
    }
}
