package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.events;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class DailyTransactionStored extends DomainEvent {
    @Getter
    private final DailyTransaction transaction;

    public DailyTransactionStored(DailyTransaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }
}
