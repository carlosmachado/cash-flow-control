package br.com.cmachado.cashflowcontrol.domain.model.balance;

import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
@Getter
public class BalanceId implements ValueObject<BalanceId> {
    @NotNull(message = "value is required")
    @Column(name = "balance_id", columnDefinition = "uuid", nullable = false)
    private UUID value;

    protected BalanceId() {
    }

    private BalanceId(UUID value) {
        this.value = value;
    }

    public static BalanceId code(UUID value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new BalanceId(value);
    }

    public static BalanceId generate() {
        return new BalanceId(UUID.randomUUID());
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

