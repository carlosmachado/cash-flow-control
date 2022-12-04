package br.com.cmachado.cashflowcontrol.infrastructure.outbox;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "OutBox")
@Table(name = "outbox",
        schema = "cash-flow",
        indexes = {
                @Index(name = "outbox_idx_aggregate_created_at_dispatched", columnList = "aggregate, created_at, dispatched"),
                @Index(name = "outbox_idx_dispatched_created_at", columnList = "dispatched, created_at"),
                @Index(name = "outbox_idx_aggregate_id", columnList = "aggregate_id")
        })
public class OutBox {
    @Id
    @Getter
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    protected UUID id;

    @Getter
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Getter
    @NotNull(message = "aggregate is required")
    @Column(name = "aggregate", nullable = false, length = 500)
    private String aggregate;

    @Getter
    @Column(name = "aggregate_id", length = 500)
    private String aggregateId;

    @Getter
    @NotNull(message = "operation is required")
    @Column(name = "operation", nullable = false, length = 500)
    private String operation;

    @Getter
    @NotNull(message = "message is required")
    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Getter
    @NotNull(message = "dispatched is required")
    @Column(name = "dispatched", nullable = false)
    private boolean dispatched;

    @Getter
    @Column(name = "dispatched_at")
    private LocalDateTime dispatchedAt;

    protected OutBox() {
    }

    public OutBox(String aggregate,
                  String aggregateId,
                  String operation,
                  String message) {
        this.aggregate = aggregate;
        this.aggregateId = aggregateId;
        this.operation = operation;
        this.message = message;
        this.dispatched = false;
        this.dispatchedAt = null;
    }

    public static OutBox of(String aggregateId,
                            String aggregate,
                            String operation,
                            OutBoxMessages.Message message) {
        return new OutBox(
                aggregate,
                aggregateId,
                operation,
                message.getValue()
        );
    }

    public void dispatch() {
        this.dispatched = true;
        this.dispatchedAt = LocalDateTime.now();
    }

    public void retry() {
        this.dispatched = false;
        this.dispatchedAt = null;
    }
}
