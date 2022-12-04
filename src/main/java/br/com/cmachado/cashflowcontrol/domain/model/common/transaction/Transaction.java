package br.com.cmachado.cashflowcontrol.domain.model.common.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Currency;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.shared.AggregateRootBase;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Transaction extends AggregateRootBase<Transaction> {
    @Getter
    @EmbeddedId
    @NotNull(message = "id is required")
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid", nullable = false))
    private TransactionId id;

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

    @Override
    public boolean sameIdentityAs(Transaction other) {
        return other != null && other.getId().equals(id);
    }
}
