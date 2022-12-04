package br.com.cmachado.cashflowcontrol.domain.model.transaction.events;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class TransactionRegistered extends DomainEvent {
    @Getter
    private final Transaction transaction;

    public TransactionRegistered(Transaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }
}
