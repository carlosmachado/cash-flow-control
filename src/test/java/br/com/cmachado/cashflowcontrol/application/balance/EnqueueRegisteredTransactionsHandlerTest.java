package br.com.cmachado.cashflowcontrol.application.balance;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.TransactionRegistered;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBoxMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnqueueRegisteredTransactionsHandlerTest {

    @Mock
    private OutBoxMessages outBoxMessages;

    @Test
    public void transaction_registered_should_be_enqueued(){
        var target = new EnqueueRegisteredTransactionsHandler(outBoxMessages);

        var transaction = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        var domainEvent = (TransactionRegistered) transaction.getUncommitedEvents().stream().findFirst().get();

        when(outBoxMessages.toConsolidateBalance(any(TransactionId.class)))
                .thenReturn(new OutBoxMessages.Message("json"));

        target.handle(domainEvent);

        verify(outBoxMessages, times(1))
                .toConsolidateBalance(any(TransactionId.class));

        verify(outBoxMessages, times(1))
                .enqueue(anyString(), anyString(), anyString(), any(OutBoxMessages.Message.class));
    }
}
