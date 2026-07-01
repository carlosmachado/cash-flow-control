package br.com.cmachado.cashflow.transaction.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class TransactionIdTest {

    @Test
    void generate_returns_non_null_uuid() {
        var id = TransactionId.generate();
        assertThat(id).isNotNull();
        assertThat(id.getValue()).isNotNull();
    }

    @Test
    void generate_is_monotonic_ulid_ordered() {
        var a = TransactionId.generate();
        var b = TransactionId.generate();
        assertThat(a.getValue().compareTo(b.getValue())).isLessThanOrEqualTo(0);
    }

    @Test
    void code_wraps_given_uuid() {
        var uuid = UUID.randomUUID();
        var id = TransactionId.code(uuid);
        assertThat(id.getValue()).isEqualTo(uuid);
    }

    @Test
    void code_rejects_null() {
        assertThatNullPointerException().isThrownBy(() -> TransactionId.code(null));
    }

    @Test
    void equals_same_uuid() {
        var uuid = UUID.randomUUID();
        assertThat(TransactionId.code(uuid)).isEqualTo(TransactionId.code(uuid));
    }

    @Test
    void equals_different_uuid() {
        assertThat(TransactionId.generate()).isNotEqualTo(TransactionId.generate());
    }

    @Test
    void hashCode_consistent_with_equals() {
        var uuid = UUID.randomUUID();
        assertThat(TransactionId.code(uuid).hashCode()).isEqualTo(TransactionId.code(uuid).hashCode());
    }

    @Test
    void sameValueAs_true_for_equal_ids() {
        var uuid = UUID.randomUUID();
        var a = TransactionId.code(uuid);
        var b = TransactionId.code(uuid);
        assertThat(a.sameValueAs(b)).isTrue();
    }

    @Test
    void sameValueAs_false_for_different_ids() {
        assertThat(TransactionId.generate().sameValueAs(TransactionId.generate())).isFalse();
    }

    @Test
    void sameValueAs_false_for_null() {
        assertThat(TransactionId.generate().sameValueAs(null)).isFalse();
    }

    @Test
    void toString_returns_uuid_string() {
        var uuid = UUID.randomUUID();
        assertThat(TransactionId.code(uuid).toString()).isEqualTo(uuid.toString());
    }
}
