package br.com.cmachado.cashflow.shared.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {

    @Test
    void can_create_money() {
        assertThat(Money.of(new BigDecimal("1.00")).getValue()).isEqualTo(new BigDecimal("1.00"));
        assertThat(Money.of(new BigDecimal("-1.00")).getValue()).isEqualTo(new BigDecimal("-1.00"));
    }

    @Test
    void force_money_scale_2() {
        assertThrows(MoneyInvalidException.class, () -> Money.of(new BigDecimal(9391.72)));
    }

    @Test
    void equals_verifies_value_not_instance() {
        var money1 = Money.of("5.00");
        var money2 = Money.of("5.00");

        assertThat(money1).isEqualTo(money2);
        assertTrue(money1.sameValueAs(money2));
    }

    @Test
    void sum_adds_values() {
        assertThat(Money.of("10.00").sum(Money.of("5.50")).getValue())
                .isEqualByComparingTo(new BigDecimal("15.50"));
    }

    @Test
    void sum_with_negative_subtracts() {
        assertThat(Money.of("10.00").sum(Money.of("-4.00")).getValue())
                .isEqualByComparingTo(new BigDecimal("6.00"));
    }

    @Test
    void isNegative_handles_fractional_values() {
        assertThat(Money.isNegative(Money.of("-0.50"))).isTrue();
        assertThat(Money.isNegative(Money.of("0.50"))).isFalse();
        assertThat(Money.isNegative(Money.of("0.00"))).isFalse();
    }
}
