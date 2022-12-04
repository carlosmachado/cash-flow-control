package br.com.cmachado.cashflowcontrol.domain.model.debit;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.Transaction;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "debit",
        schema = "cash-flow",
        indexes = {
                @Index(name = "debit_idx_type", columnList = "type"),
                @Index(name = "debit_idx_created_at", columnList = "created_at")
})
public class Debit extends Transaction {


    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type is required")
    @Column(name = "type", length = 50, nullable = false)
    private Type type;


    public enum Type {
        TAX,
        PURCHASE
    }
}
