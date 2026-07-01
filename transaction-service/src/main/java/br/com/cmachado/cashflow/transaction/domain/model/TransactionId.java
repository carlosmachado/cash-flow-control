package br.com.cmachado.cashflow.transaction.domain.model;

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
public class TransactionId implements ValueObject<TransactionId> {
    @NotNull(message = "value is required")
    @Column(name = "transaction_id", columnDefinition = "uuid", nullable = false)
    private UUID value;

    private TransactionId(UUID value) {
        this.value = value;
    }

    public static TransactionId code(UUID value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new TransactionId(value);
    }

    public static TransactionId generate() {
        return new TransactionId(UlidCreator.getMonotonicUlid().toUuid());
    }

    @Override
    public boolean sameValueAs(TransactionId other) {
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
