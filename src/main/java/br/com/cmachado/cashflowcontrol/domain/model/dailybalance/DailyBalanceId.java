package br.com.cmachado.cashflowcontrol.domain.model.dailybalance;

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
public class DailyBalanceId implements ValueObject<DailyBalanceId> {
    @NotNull(message = "value is required")
    @Column(name = "daily_balance_id", columnDefinition = "uuid", nullable = false)
    private UUID value;

    protected DailyBalanceId() {
    }

    private DailyBalanceId(UUID value) {
        this.value = value;
    }

    public static DailyBalanceId code(UUID value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new DailyBalanceId(value);
    }

    public static DailyBalanceId generate() {
        return new DailyBalanceId(UUID.randomUUID());
    }

    @Override
    public boolean sameValueAs(DailyBalanceId other) {
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

