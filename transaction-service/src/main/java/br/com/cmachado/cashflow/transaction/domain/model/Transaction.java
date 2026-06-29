package br.com.cmachado.cashflow.transaction.domain.model;

import br.com.cmachado.cashflow.shared.ddd.AggregateRootBase;
import br.com.cmachado.cashflow.shared.money.Currency;
import br.com.cmachado.cashflow.shared.money.Money;
import br.com.cmachado.cashflow.transaction.domain.model.events.TransactionRegistered;
import br.com.cmachado.cashflow.transaction.domain.model.types.Credit;
import br.com.cmachado.cashflow.transaction.domain.model.types.Debit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction",
        schema = "transaction",
        indexes = {
                @Index(name = "transaction_idx_type", columnList = "type"),
                @Index(name = "transaction_idx_created_at", columnList = "created_at"),
                @Index(name = "transaction_idx_transaction_date", columnList = "transaction_date")
        })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends AggregateRootBase<Transaction> {
    public static final String TRANSACTION_AGGREGATE = "TRANSACTION";
    public static final String REGISTERED_OPERATION = "REGISTERED";

    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private TransactionId id;

    @Column(name = "type", nullable = false, updatable = false, insertable = false, length = 50)
    private String type;//hibernate @DiscriminatorColumn

    @Getter
    @NotNull(message = "createdAt is required")
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

    protected Transaction() {
    }

    protected Transaction(TransactionId id,
                          TransactionDate transactionDate,
                          Money amount,
                          String description) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.description = description;
        this.currency = Currency.BRL;
        this.createdAt = LocalDateTime.now();
        registerEvent(new TransactionRegistered(this));
    }

    @Override
    public boolean sameIdentityAs(Transaction other) {
        return other != null && other.getId().equals(id);
    }

    public abstract Type type();

    public String getType() {
        return type().name();
    }

    public enum Type implements TransactionFactory {
        CREDIT {
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
        DEBIT {
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
