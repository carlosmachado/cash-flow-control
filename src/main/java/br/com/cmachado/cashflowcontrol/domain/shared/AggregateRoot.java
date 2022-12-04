package br.com.cmachado.cashflowcontrol.domain.shared;

import java.util.Collection;

public interface AggregateRoot<T> extends Entity<T> {
    Collection<DomainEvent> getUncommitedEvents();

    void clearEvents();
}

