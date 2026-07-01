package br.com.cmachado.cashflow.transaction.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class TransactionDateTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2025, 1, 1, 10, 0);

    @Test
    void was_creates_with_given_value() {
        var date = TransactionDate.was(NOW);
        assertThat(date.getValue()).isEqualTo(NOW);
    }

    @Test
    void was_rejects_null() {
        assertThatNullPointerException().isThrownBy(() -> TransactionDate.was(null));
    }

    @Test
    void equals_same_datetime() {
        assertThat(TransactionDate.was(NOW)).isEqualTo(TransactionDate.was(NOW));
    }

    @Test
    void equals_different_datetime() {
        assertThat(TransactionDate.was(NOW)).isNotEqualTo(TransactionDate.was(NOW.plusDays(1)));
    }

    @Test
    void hashCode_consistent_with_equals() {
        assertThat(TransactionDate.was(NOW).hashCode()).isEqualTo(TransactionDate.was(NOW).hashCode());
    }

    @Test
    void sameValueAs_true_for_equal_dates() {
        assertThat(TransactionDate.was(NOW).sameValueAs(TransactionDate.was(NOW))).isTrue();
    }

    @Test
    void sameValueAs_false_for_different_dates() {
        assertThat(TransactionDate.was(NOW).sameValueAs(TransactionDate.was(NOW.plusHours(1)))).isFalse();
    }

    @Test
    void sameValueAs_false_for_null() {
        assertThat(TransactionDate.was(NOW).sameValueAs(null)).isFalse();
    }

    @Test
    void toString_returns_datetime_string() {
        assertThat(TransactionDate.was(NOW).toString()).isEqualTo(NOW.toString());
    }
}
