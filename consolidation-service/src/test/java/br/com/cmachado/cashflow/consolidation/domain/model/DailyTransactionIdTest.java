package br.com.cmachado.cashflow.consolidation.domain.model;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransactionId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class DailyTransactionIdTest {

    @Test
    void generate_returns_non_null() {
        assertThat(DailyTransactionId.generate().getValue()).isNotNull();
    }

    @Test
    void generate_is_monotonic_ulid_ordered() {
        var a = DailyTransactionId.generate();
        var b = DailyTransactionId.generate();
        assertThat(a.getValue().compareTo(b.getValue())).isLessThanOrEqualTo(0);
    }

    @Test
    void code_wraps_given_uuid() {
        var uuid = UUID.randomUUID();
        assertThat(DailyTransactionId.code(uuid).getValue()).isEqualTo(uuid);
    }

    @Test
    void code_rejects_null() {
        assertThatNullPointerException().isThrownBy(() -> DailyTransactionId.code(null));
    }

    @Test
    void equals_same_uuid() {
        var uuid = UUID.randomUUID();
        assertThat(DailyTransactionId.code(uuid)).isEqualTo(DailyTransactionId.code(uuid));
    }

    @Test
    void equals_different_uuid() {
        assertThat(DailyTransactionId.generate()).isNotEqualTo(DailyTransactionId.generate());
    }

    @Test
    void hashCode_consistent_with_equals() {
        var uuid = UUID.randomUUID();
        assertThat(DailyTransactionId.code(uuid).hashCode()).isEqualTo(DailyTransactionId.code(uuid).hashCode());
    }

    @Test
    void sameValueAs_true() {
        var uuid = UUID.randomUUID();
        assertThat(DailyTransactionId.code(uuid).sameValueAs(DailyTransactionId.code(uuid))).isTrue();
    }

    @Test
    void sameValueAs_false_different() {
        assertThat(DailyTransactionId.generate().sameValueAs(DailyTransactionId.generate())).isFalse();
    }

    @Test
    void sameValueAs_false_null() {
        assertThat(DailyTransactionId.generate().sameValueAs(null)).isFalse();
    }

    @Test
    void toString_returns_uuid_string() {
        var uuid = UUID.randomUUID();
        assertThat(DailyTransactionId.code(uuid).toString()).isEqualTo(uuid.toString());
    }
}
