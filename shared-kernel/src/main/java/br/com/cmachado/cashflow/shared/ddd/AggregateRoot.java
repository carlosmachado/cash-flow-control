package br.com.cmachado.cashflow.shared.ddd;

import java.util.Collection;

public interface AggregateRoot<T> extends Entity<T> {
    Collection<DomainEvent> getUncommitedEvents();

    void clearEvents();
}
