package br.com.cmachado.cashflowcontrol.application.balance;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.TransactionRegistered;
import br.com.cmachado.cashflowcontrol.domain.shared.SubscribeTo;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBoxMessages;
import org.springframework.stereotype.Component;

@Component
public class BalanceUpdateHandler implements SubscribeTo<TransactionRegistered> {
    public static final String TRANSACTION_AGGREGATE = Transaction.class.getName().toUpperCase();
    public static final String REGISTERED_OPERATION = "REGISTERED";

    private final OutBoxMessages outBoxMessages;

    public BalanceUpdateHandler(OutBoxMessages outBoxMessages) {
        this.outBoxMessages = outBoxMessages;
    }

    @Override
    public void handle(TransactionRegistered event) {
        var transactionId = event.getTransaction().getId();
        var message = outBoxMessages.toConsolidateBalance(transactionId);
        outBoxMessages.enqueue(
                transactionId.toString(),
                TRANSACTION_AGGREGATE,
                REGISTERED_OPERATION,
                message
        );
    }
}
