package br.com.cmachado.cashflow.consolidation.domain.service;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransactionRepository;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyTransactionReportServiceTest {

    @Mock
    DailyTransactionRepository repository;

    private TransactionRegisteredMessage message(UUID id) {
        return new TransactionRegisteredMessage(
                id.toString(), "CREDIT", new BigDecimal("100.00"), "BRL", LocalDateTime.now());
    }

    @Test
    void stores_new_daily_transaction() {
        var id = UUID.randomUUID();
        when(repository.existsByTransactionId(id)).thenReturn(false);

        new DailyTransactionReportService(repository).store(message(id));

        verify(repository).save(org.mockito.ArgumentMatchers.any(DailyTransaction.class));
    }

    @Test
    void is_idempotent_when_transaction_already_stored() {
        var id = UUID.randomUUID();
        when(repository.existsByTransactionId(id)).thenReturn(true);

        new DailyTransactionReportService(repository).store(message(id));

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }
}
