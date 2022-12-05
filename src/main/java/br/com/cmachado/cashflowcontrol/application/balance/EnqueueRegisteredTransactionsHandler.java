package br.com.cmachado.cashflowcontrol.application.balance;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.TransactionRegistered;
import br.com.cmachado.cashflowcontrol.domain.shared.SubscribeTo;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBoxMessages;
import org.springframework.stereotype.Component;

@Component
public class EnqueueRegisteredTransactionsHandler implements SubscribeTo<TransactionRegistered> {
    private final OutBoxMessages outBoxMessages;

    public EnqueueRegisteredTransactionsHandler(OutBoxMessages outBoxMessages) {
        this.outBoxMessages = outBoxMessages;
    }

    @Override
    public void handle(TransactionRegistered event) {
        var transactionId = event.getTransaction().getId();

        var message = outBoxMessages.toConsolidateBalance(transactionId);

        outBoxMessages.enqueue(
                transactionId.toString(),
                Transaction.TRANSACTION_AGGREGATE,
                Transaction.REGISTERED_OPERATION,
                message
        );
    }
}
