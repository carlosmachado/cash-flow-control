package br.com.cmachado.cashflow.transaction.application;

import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.json.JsonSupport;
import br.com.cmachado.cashflow.shared.money.Money;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionDate;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionId;
import br.com.cmachado.cashflow.transaction.domain.model.events.TransactionRegistered;
import br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBoxMessages;
import br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBoxRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EnqueueRegisteredTransactionsHandlerTest {

    @Test
    void enqueues_full_transaction_state_as_message() {
        var captured = new java.util.concurrent.atomic.AtomicReference<String>();

        OutBoxRepository repository = org.mockito.Mockito.mock(OutBoxRepository.class);
        org.mockito.Mockito.when(repository.save(org.mockito.ArgumentMatchers.any()))
                .thenAnswer(invocation -> {
                    var outbox = invocation.getArgument(0,
                            br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBox.class);
                    captured.set(outbox.getMessage());
                    return outbox;
                });

        var outBoxMessages = new OutBoxMessages(repository);
        var handler = new EnqueueRegisteredTransactionsHandler(outBoxMessages);

        var transaction = Transaction.Type.CREDIT.register(
                TransactionId.generate(),
                TransactionDate.was(LocalDateTime.now()),
                Money.of("100.00"),
                "deposit");

        handler.handle(new TransactionRegistered(transaction));

        var message = JsonSupport.gson().fromJson(captured.get(), TransactionRegisteredMessage.class);
        assertThat(message.getType()).isEqualTo("CREDIT");
        assertThat(message.getAmount()).isEqualByComparingTo("100.00");
        assertThat(message.getCurrency()).isEqualTo("BRL");
        assertThat(message.getTransactionId()).isEqualTo(transaction.getId().toString());
    }
}
