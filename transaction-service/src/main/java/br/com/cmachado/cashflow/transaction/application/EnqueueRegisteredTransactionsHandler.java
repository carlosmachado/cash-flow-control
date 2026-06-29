package br.com.cmachado.cashflow.transaction.application;

import br.com.cmachado.cashflow.shared.ddd.SubscribeTo;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.events.TransactionRegistered;
import br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBoxMessages;
import org.springframework.stereotype.Component;

@Component
public class EnqueueRegisteredTransactionsHandler implements SubscribeTo<TransactionRegistered> {
    private final OutBoxMessages outBoxMessages;

    public EnqueueRegisteredTransactionsHandler(OutBoxMessages outBoxMessages) {
        this.outBoxMessages = outBoxMessages;
    }

    @Override
    public void handle(TransactionRegistered event) {
        var transaction = event.getTransaction();

        var message = outBoxMessages.toRegisteredMessage(transaction);

        outBoxMessages.enqueue(
                transaction.getId().toString(),
                Transaction.TRANSACTION_AGGREGATE,
                Transaction.REGISTERED_OPERATION,
                message
        );
    }
}
