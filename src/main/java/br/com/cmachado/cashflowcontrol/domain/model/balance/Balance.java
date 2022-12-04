package br.com.cmachado.cashflowcontrol.domain.model.balance;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import br.com.cmachado.cashflowcontrol.domain.model.debit.Debit;
import br.com.cmachado.cashflowcontrol.domain.shared.AggregateRootBase;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance", schema = "cash-flow")
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

    protected Balance(){}

    private Balance(BalanceId id,
                    Currency currency,
                    Money amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
    }

    public static Balance start() {
        return new Balance(
                BalanceId.generate(),
                Currency.BRL,
                Money.ZERO
        );
    }


    @Override
    public boolean sameIdentityAs(Balance other) {
        return other != null && other.getId().equals(id);
    }

    public void sum(Credit credit) {
        this.amount = this.amount.sum(credit.getAmount());
    }

    public void subtract(Debit debit) {
        this.amount = this.amount.subtract(debit.getAmount());
    }
}
