package br.com.cmachado.cashflowcontrol.domain.model.debit;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.debit.events.DebitDeleted;
import br.com.cmachado.cashflowcontrol.domain.model.debit.events.DebitRegistered;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "debit",
        schema = "cash-flow",
        indexes = {
                @Index(name = "debit_idx_type", columnList = "type"),
                @Index(name = "debit_idx_created_at", columnList = "created_at"),
                @Index(name = "debit_idx_transaction_date", columnList = "transaction_date")
})
public class Debit extends Transaction {


    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type is required")
    @Column(name = "type", length = 50, nullable = false)
    private Type type;

    protected Debit(){}

    private Debit(TransactionId transactionId,
                  TransactionDate transactionDate,
                  Debit.Type type,
                  Money amount,
                  String description) {
        super(transactionId, transactionDate, amount, description);
        this.type = type;
        registerEvent(new DebitRegistered(this));
    }

    public static Debit register(TransactionId transactionId,
                                 TransactionDate transactionDate,
                                 Debit.Type type,
                                 Money amount,
                                 String description) {
        return new Debit(
                transactionId,
                transactionDate,
                type,
                amount,
                description
        );
    }

    public void delete() {
        registerEvent(new DebitDeleted(this));
    }

    public enum Type {
        TAX,
        PURCHASE
    }
}
