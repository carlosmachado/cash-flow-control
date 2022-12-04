package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.types.Credit;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.types.Debit;
import br.com.cmachado.cashflowcontrol.domain.shared.AggregateRootBase;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction",
        schema = "cash-flow",
        indexes = {
                @Index(name = "transaction_idx_type", columnList = "type"),
                @Index(name = "transaction_idx_created_at", columnList = "created_at"),
                @Index(name = "transaction_idx_transaction_date", columnList = "transaction_date")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends AggregateRootBase<Transaction> {
    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private TransactionId id;

    @Column(name = "type", nullable = false, updatable = false, insertable = false, length = 50)
    private String type;//hibernate @DiscriminatorColumn

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Embedded
    @NotNull(message = "transactionDate is required")
    private TransactionDate transactionDate;

    @Getter
    @Embedded
    @NotNull(message = "currency is required")
    private Currency currency;

    @Getter
    @Embedded
    @NotNull(message = "amount is required")
    private Money amount;

    @Getter
    @NotNull(message = "description is required")
    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    protected Transaction(){}

    protected Transaction(TransactionId id,
                          TransactionDate transactionDate,
                          Money amount,
                          String description) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public boolean sameIdentityAs(Transaction other) {
        return other != null && other.getId().equals(id);
    }

    public abstract Type type();

    public abstract void update(Balance balance);

    public enum Type implements TransactionFactory {
        CREDIT{
            @Override
            public Transaction register(TransactionId transactionId,
                                        TransactionDate transactionDate,
                                        Money amount,
                                        String description) {

                var creditAmount = Money.isNegative(amount)
                        ? amount.multiply(-1)
                        : amount;

                return Credit.register(
                        transactionId,
                        transactionDate,
                        creditAmount,
                        description
                );
            }
        },
        DEBIT{
            public Transaction register(TransactionId transactionId,
                                        TransactionDate transactionDate,
                                        Money amount,
                                        String description) {
                var debitAmount = !Money.isNegative(amount)
                        ? amount.multiply(-1)
                        : amount;

                return Debit.register(
                        transactionId,
                        transactionDate,
                        debitAmount,
                        description
                );
            }
        }
    }
}
