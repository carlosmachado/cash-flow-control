package br.com.cmachado.cashflow.transaction.domain.model.events;

import br.com.cmachado.cashflow.shared.ddd.DomainEvent;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import lombok.Getter;

public class TransactionRegistered extends DomainEvent {
    @Getter
    private final transient Transaction transaction;

    public TransactionRegistered(Transaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }
}
