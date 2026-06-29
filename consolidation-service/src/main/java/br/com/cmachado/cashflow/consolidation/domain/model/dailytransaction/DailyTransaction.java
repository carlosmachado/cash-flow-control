package br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.events.DailyTransactionStored;
import br.com.cmachado.cashflow.shared.ddd.AggregateRootBase;
import br.com.cmachado.cashflow.shared.money.Currency;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "daily_transaction",
        schema = "consolidation",
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
    @NotNull(message = "transactionId is required")
    @Column(name = "transaction_id", columnDefinition = "uuid", nullable = false)
    private UUID transactionId;

    @Getter
    @NotNull(message = "transactionDate is required")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Getter
    @NotNull(message = "date is required")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    protected DailyTransaction() {
    }

    private DailyTransaction(DailyTransactionId id,
                            UUID transactionId,
                            LocalDateTime transactionDate,
                            Currency currency,
                            Money amount) {
        this.id = id;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.date = transactionDate.toLocalDate();
        this.currency = currency;
        this.amount = amount;
        registerEvent(new DailyTransactionStored(this));
    }

    public static DailyTransaction store(UUID transactionId,
                                         LocalDateTime transactionDate,
                                         Currency currency,
                                         Money amount) {
        return new DailyTransaction(
                DailyTransactionId.generate(),
                transactionId,
                transactionDate,
                currency,
                amount
        );
    }

    @Override
    public boolean sameIdentityAs(DailyTransaction other) {
        return other != null && other.getId().equals(id);
    }
}
