package br.com.cmachado.cashflow.consolidation.domain.model.balance.events;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.shared.ddd.DomainEvent;
import lombok.Getter;

public class BalanceUpdated extends DomainEvent {
    @Getter
    private final transient Balance balance;

    public BalanceUpdated(Balance balance) {
        super(balance);
        this.balance = balance;
    }
}
