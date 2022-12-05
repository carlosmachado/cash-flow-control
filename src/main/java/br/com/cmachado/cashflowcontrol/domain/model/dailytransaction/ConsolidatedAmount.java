package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
@Getter
public class ConsolidatedAmount implements ValueObject<ConsolidatedAmount> {
    @NotNull(message = "value is required")
    @Column(name = "consolidated_amount", nullable = false)
    private Money value;

    protected ConsolidatedAmount() {
    }

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

