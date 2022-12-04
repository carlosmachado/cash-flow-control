package br.com.cmachado.cashflowcontrol.domain.shared;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AggregateRootBase<T>
        extends AbstractAggregateRoot
        implements AggregateRoot<T> {
    @Transient
    private final Collection<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    @AfterDomainEventPublication
    public void clearEvents() {
        domainEvents.clear();
    }

    @DomainEvents
    public Collection<DomainEvent> getUncommitedEvents() {
        return domainEvents;
    }
}
