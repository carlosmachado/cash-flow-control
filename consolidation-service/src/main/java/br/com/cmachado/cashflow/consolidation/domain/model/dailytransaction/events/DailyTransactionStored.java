package br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.events;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflow.shared.ddd.DomainEvent;
import lombok.Getter;

public class DailyTransactionStored extends DomainEvent {
    @Getter
    private final transient DailyTransaction transaction;

    public DailyTransactionStored(DailyTransaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }
}
