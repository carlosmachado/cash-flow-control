package br.com.cmachado.cashflowcontrol.domain.model.dailybalance;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.types.Credit;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.types.Debit;
import br.com.cmachado.cashflowcontrol.domain.shared.AggregateRootBase;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_balance",
        schema = "cash-flow",
        indexes = {
                @Index(name = "daily_balance_idx_date", columnList = "date"),
                @Index(name = "daily_balance_idx_transaction_date", columnList = "transaction_date")
}, uniqueConstraints = {
        @UniqueConstraint(name = "daily_balance_unq_transaction_id", columnNames = {"transaction_id"})
})
public class DailyBalance extends AggregateRootBase<DailyBalance> {
    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private DailyBalanceId id;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Embedded
    @NotNull(message = "currency is required")
    private Currency currency;

    @Getter
    @Embedded
    @NotNull(message = "amount is required")
    private Money amount;

    @Getter
    @Embedded
    @NotNull(message = "transactionId is required")
    private TransactionId transactionId;

    protected DailyBalance(){}

    private DailyBalance(DailyBalanceId id,
                         Currency currency,
                         Money amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
    }

    public static DailyBalance start() {
        return new DailyBalance(
                DailyBalanceId.generate(),
                Currency.BRL,
                Money.ZERO
        );
    }

    @Override
    public boolean sameIdentityAs(DailyBalance other) {
        return other != null && other.getId().equals(id);
    }

    public void sum(Credit credit) {
        this.amount = this.amount.sum(credit.getAmount());
    }

    public void subtract(Debit debit) {
        this.amount = this.amount.subtract(debit.getAmount());
    }
}
