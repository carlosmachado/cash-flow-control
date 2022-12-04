package br.com.cmachado.cashflowcontrol.domain.shared;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public interface SubscribeTo<T extends DomainEvent> {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    void handle(T event);
}
