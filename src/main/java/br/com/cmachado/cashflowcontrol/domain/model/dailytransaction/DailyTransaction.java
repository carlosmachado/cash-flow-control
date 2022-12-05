package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.events.DailyTransactionStored;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
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
@Table(name = "daily_transaction",
        schema = "cash_flow",
        indexes = {
                @Index(name = "daily_transaction_idx_date", columnList = "date")
}, uniqueConstraints = {
        @UniqueConstraint(name = "daily_transaction_unq_transaction_id", columnNames = {"transaction_id"})
})
public class DailyTransaction extends AggregateRootBase<DailyTransaction> {
    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private DailyTransactionId id;

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

    @Getter
    @Embedded
    @NotNull(message = "transactionDate is required")
    private TransactionDate transactionDate;

    @Getter
    @NotNull(message = "date is required")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    protected DailyTransaction(){}

    private DailyTransaction(DailyTransactionId id,
                             TransactionId transactionId,
                             TransactionDate transactionDate,
                             Currency currency,
                             Money amount) {
        this.id = id;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.date = transactionDate.getValue().toLocalDate();
        this.currency = currency;
        this.amount = amount;
        registerEvent(new DailyTransactionStored(this));
    }

    public static DailyTransaction store(Transaction transaction) {
        return new DailyTransaction(
                DailyTransactionId.generate(),
                transaction.getId(),
                transaction.getTransactionDate(),
                transaction.getCurrency(),
                transaction.getAmount()
        );
    }

    @Override
    public boolean sameIdentityAs(DailyTransaction other) {
        return other != null && other.getId().equals(id);
    }
}
