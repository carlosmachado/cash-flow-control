package br.com.cmachado.cashflow.consolidation.domain.model.balance;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.events.BalanceUpdated;
import br.com.cmachado.cashflow.shared.ddd.AggregateRootBase;
import br.com.cmachado.cashflow.shared.money.Currency;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "balance", schema = "consolidation")
public class Balance extends AggregateRootBase<Balance> {
    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private BalanceId id;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Getter
    @Embedded
    @NotNull(message = "currency is required")
    private Currency currency;

    @Getter
    @Embedded
    @NotNull(message = "amount is required")
    private Money amount;

    protected Balance() {
    }

    private Balance(BalanceId id,
                    Currency currency,
                    Money amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        registerEvent(new BalanceUpdated(this));
    }

    public static Balance start() {
        return new Balance(
                BalanceId.singleton(),
                Currency.BRL,
                Money.ZERO
        );
    }

    @Override
    public boolean sameIdentityAs(Balance other) {
        return other != null && other.getId().equals(id);
    }

    /**
     * Applies a signed transaction amount (credit positive, debit negative) to
     * the running balance.
     */
    public void apply(Money signedAmount) {
        this.amount = this.amount.sum(signedAmount);
        registerEvent(new BalanceUpdated(this));
    }
}
