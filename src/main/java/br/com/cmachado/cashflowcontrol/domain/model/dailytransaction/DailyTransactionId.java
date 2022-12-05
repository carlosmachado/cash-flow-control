package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

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
public class DailyTransactionId implements ValueObject<DailyTransactionId> {
    @NotNull(message = "value is required")
    @Column(name = "daily_transaction_id", columnDefinition = "uuid", nullable = false)
    private UUID value;

    protected DailyTransactionId() {
    }

    private DailyTransactionId(UUID value) {
        this.value = value;
    }

    public static DailyTransactionId code(UUID value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new DailyTransactionId(value);
    }

    public static DailyTransactionId generate() {
        return new DailyTransactionId(UUID.randomUUID());
    }

    @Override
    public boolean sameValueAs(DailyTransactionId other) {
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

