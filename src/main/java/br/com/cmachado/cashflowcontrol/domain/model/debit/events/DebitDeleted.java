package br.com.cmachado.cashflowcontrol.domain.model.debit.events;

import br.com.cmachado.cashflowcontrol.domain.model.debit.Debit;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class DebitDeleted extends DomainEvent {
    @Getter
    private final Debit debit;

    public DebitDeleted(Debit debit) {
        super(debit);
        this.debit = debit;
    }
}
