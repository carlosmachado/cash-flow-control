package br.com.cmachado.cashflow.consolidation.domain.model.balance;

import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.github.f4b6a3.ulid.UlidCreator;

import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BalanceId implements ValueObject<BalanceId> {
    @NotNull(message = "value is required")
    @Column(name = "balance_id", columnDefinition = "uuid", nullable = false)
    private UUID value;

    private BalanceId(UUID value) {
        this.value = value;
    }

    /** The balance is a single running total — one deterministic row. */
    private static final UUID SINGLETON = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static BalanceId code(UUID value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new BalanceId(value);
    }

    public static BalanceId singleton() {
        return new BalanceId(SINGLETON);
    }

    public static BalanceId generate() {
        return new BalanceId(UlidCreator.getMonotonicUlid().toUuid());
    }

    @Override
    public boolean sameValueAs(BalanceId other) {
        return other != null && other.equals(this);
    }

    public String getValueString() {
        return value.toString();
    }

    @Override
    public String toString() {
        return getValueString();
    }
}
