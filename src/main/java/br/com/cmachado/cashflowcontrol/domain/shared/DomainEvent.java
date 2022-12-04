package br.com.cmachado.cashflowcontrol.domain.shared;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public abstract class DomainEvent extends ApplicationEvent {
    @Getter
    private final LocalDateTime when;

    protected DomainEvent(Object source) {
        super(source);
        this.when = LocalDateTime.now();
    }
}
