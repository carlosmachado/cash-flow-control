package br.com.cmachado.cashflowcontrol.domain.model.credit;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.credit.events.CreditDeleted;
import br.com.cmachado.cashflowcontrol.domain.model.credit.events.CreditRegistered;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "credit",
        schema = "cash-flow",
        indexes = {
                @Index(name = "credit_idx_type", columnList = "type"),
                @Index(name = "credit_idx_created_at", columnList = "created_at"),
                @Index(name = "credit_idx_transaction_date", columnList = "transaction_date")
})
public class Credit extends Transaction {

    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type is required")
    @Column(name = "type", length = 50, nullable = false)
    private Type type;

    protected Credit(){}

    private Credit(TransactionId transactionId,
                   TransactionDate transactionDate,
                   Type type,
                   Money amount,
                   String description) {
        super(transactionId, transactionDate, amount, description);
        this.type = type;
        registerEvent(new CreditRegistered(this));
    }

    public static Credit register(TransactionId transactionId,
                                  TransactionDate transactionDate,
                                  Type type,
                                  Money amount,
                                  String description) {
        return new Credit(
                transactionId,
                transactionDate,
                type,
                amount,
                description
        );
    }

    public void delete() {
        registerEvent(new CreditDeleted(this));
    }

    public enum Type {
        SALE,
        SERVICE
    }
}
