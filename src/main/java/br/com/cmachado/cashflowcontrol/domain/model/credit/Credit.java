package br.com.cmachado.cashflowcontrol.domain.model.credit;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.Transaction;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "credit",
        schema = "cash-flow",
        indexes = {
                @Index(name = "credit_idx_type", columnList = "type"),
                @Index(name = "credit_idx_created_at", columnList = "created_at")
})
public class Credit extends Transaction {

    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type is required")
    @Column(name = "type", length = 50, nullable = false)
    private Type type;


    public enum Type {
        SALE,
        SERVICE
    }
}
