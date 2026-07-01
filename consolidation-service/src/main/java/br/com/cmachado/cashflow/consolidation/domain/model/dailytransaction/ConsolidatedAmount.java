package br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction;

import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsolidatedAmount implements ValueObject<ConsolidatedAmount> {
    @NotNull(message = "value is required")
    @Column(name = "consolidated_amount", nullable = false)
    private Money value;

    private ConsolidatedAmount(Money value) {
        this.value = value;
    }

    public static ConsolidatedAmount ofTransactions(Collection<DailyTransaction> transactions) {
        Objects.requireNonNull(transactions, "transactions cannot be null");

        var amount = Money.ZERO;
        for (var tra : transactions)
            amount = amount.sum(tra.getAmount());

        return new ConsolidatedAmount(amount);
    }

    @Override
    public boolean sameValueAs(ConsolidatedAmount other) {
        return other != null && other.equals(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
