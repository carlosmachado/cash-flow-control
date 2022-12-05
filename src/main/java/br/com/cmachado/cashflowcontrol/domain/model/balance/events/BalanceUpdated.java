package br.com.cmachado.cashflowcontrol.domain.model.balance.events;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainEvent;
import lombok.Getter;

public class BalanceUpdated extends DomainEvent {
    @Getter
    private final Balance balance;

    public BalanceUpdated(Balance balance) {
        super(balance);
        this.balance = balance;
    }
}
