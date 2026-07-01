package br.com.cmachado.cashflow.consolidation.domain.model;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class BalanceIdTest {

    @Test
    void generate_returns_non_null() {
        assertThat(BalanceId.generate().getValue()).isNotNull();
    }

    @Test
    void generate_is_monotonic_ulid_ordered() {
        var a = BalanceId.generate();
        var b = BalanceId.generate();
        assertThat(a.getValue().compareTo(b.getValue())).isLessThanOrEqualTo(0);
    }

    @Test
    void singleton_always_same_uuid() {
        assertThat(BalanceId.singleton()).isEqualTo(BalanceId.singleton());
    }

    @Test
    void code_wraps_given_uuid() {
        var uuid = UUID.randomUUID();
        assertThat(BalanceId.code(uuid).getValue()).isEqualTo(uuid);
    }

    @Test
    void code_rejects_null() {
        assertThatNullPointerException().isThrownBy(() -> BalanceId.code(null));
    }

    @Test
    void equals_same_uuid() {
        var uuid = UUID.randomUUID();
        assertThat(BalanceId.code(uuid)).isEqualTo(BalanceId.code(uuid));
    }

    @Test
    void equals_different_uuid() {
        assertThat(BalanceId.generate()).isNotEqualTo(BalanceId.generate());
    }

    @Test
    void hashCode_consistent_with_equals() {
        var uuid = UUID.randomUUID();
        assertThat(BalanceId.code(uuid).hashCode()).isEqualTo(BalanceId.code(uuid).hashCode());
    }

    @Test
    void sameValueAs_true() {
        var uuid = UUID.randomUUID();
        assertThat(BalanceId.code(uuid).sameValueAs(BalanceId.code(uuid))).isTrue();
    }

    @Test
    void sameValueAs_false_different() {
        assertThat(BalanceId.generate().sameValueAs(BalanceId.generate())).isFalse();
    }

    @Test
    void sameValueAs_false_null() {
        assertThat(BalanceId.generate().sameValueAs(null)).isFalse();
    }

    @Test
    void toString_returns_uuid_string() {
        var uuid = UUID.randomUUID();
        assertThat(BalanceId.code(uuid).toString()).isEqualTo(uuid.toString());
    }
}
