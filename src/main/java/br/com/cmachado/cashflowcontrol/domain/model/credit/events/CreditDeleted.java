package br.com.cmachado.cashflowcontrol.domain.model.credit.events;

import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class CreditDeleted extends DomainEvent {
    @Getter
    private final Credit credit;

    public CreditDeleted(Credit credit) {
        super(credit);
        this.credit = credit;
    }
}
