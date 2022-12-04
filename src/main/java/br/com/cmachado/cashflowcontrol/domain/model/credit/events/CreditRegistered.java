package br.com.cmachado.cashflowcontrol.domain.model.credit.events;

import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class CreditRegistered extends DomainEvent {
    @Getter
    private final Credit credit;

    public CreditRegistered(Credit credit) {
        super(credit);
        this.credit = credit;
    }
}
