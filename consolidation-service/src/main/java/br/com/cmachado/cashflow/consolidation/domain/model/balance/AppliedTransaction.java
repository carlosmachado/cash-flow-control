package br.com.cmachado.cashflow.consolidation.domain.model.balance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// Marker of transactions already applied to the balance — makes balance
// consolidation idempotent under at-least-once delivery.
@Entity
@Table(name = "balance_applied", schema = "consolidation")
public class AppliedTransaction {
    @Id
    @Getter
    @Column(name = "transaction_id", columnDefinition = "uuid", nullable = false)
    private UUID transactionId;

    @Getter
    @CreationTimestamp
    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    protected AppliedTransaction() {
    }

    public AppliedTransaction(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
