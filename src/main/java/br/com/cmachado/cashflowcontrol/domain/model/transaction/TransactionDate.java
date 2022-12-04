package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode
@Embeddable
@Getter
public class TransactionDate implements ValueObject<TransactionDate> {
    @NotNull(message = "value is required")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime value;

    protected TransactionDate() {
    }

    private TransactionDate(LocalDateTime value) {
        this.value = value;
    }

    public static TransactionDate was(LocalDateTime value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new TransactionDate(value);
    }

    @Override
    public boolean sameValueAs(TransactionDate other) {
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

