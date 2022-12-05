package br.com.cmachado.cashflowcontrol.domain.model.dailybalance;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.shared.AggregateRootBase;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_balance",
        schema = "cash_flow",
        indexes = {
                @Index(name = "daily_balance_idx_date", columnList = "date")
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
    @NotNull(message = "createdAt is required")
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

    @Getter
    @Embedded
    @NotNull(message = "transactionDate is required")
    private TransactionDate transactionDate;

    @Getter
    @NotNull(message = "date is required")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    protected DailyBalance(){}

    private DailyBalance(DailyBalanceId id,
                         Currency currency,
                         Money amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }



    @Override
    public boolean sameIdentityAs(DailyBalance other) {
        return other != null && other.getId().equals(id);
    }
}
