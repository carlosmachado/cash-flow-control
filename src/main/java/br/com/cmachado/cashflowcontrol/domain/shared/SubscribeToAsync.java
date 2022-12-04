package br.com.cmachado.cashflowcontrol.domain.shared;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public interface SubscribeToAsync<T extends DomainEvent> {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void handle(T event);
}
