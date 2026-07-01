package br.com.cmachado.cashflow.transaction.domain.model;

import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionDate implements ValueObject<TransactionDate> {
    @NotNull(message = "value is required")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime value;

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
