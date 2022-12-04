package br.com.cmachado.cashflowcontrol.infrastructure.outbox;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import lombok.Data;

@Data
public class TransactionIdMessage {
    private TransactionId transactionId;

    public TransactionIdMessage(TransactionId transactionId) {
        this.transactionId = transactionId;
    }
}
