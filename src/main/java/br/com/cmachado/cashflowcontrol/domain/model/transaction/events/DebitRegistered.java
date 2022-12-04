package br.com.cmachado.cashflowcontrol.domain.model.transaction.events;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.types.Debit;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class DebitRegistered extends DomainEvent {
    @Getter
    private final Debit debit;

    public DebitRegistered(Debit debit) {
        super(debit);
        this.debit = debit;
    }
}
