package br.com.cmachado.cashflowcontrol.domain.service;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.service.dailybalance.DailyTransactionReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DailyTransactionReportServiceTest {
    @Mock
    private DailyTransactionRepository dailyTransactionRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void store_daily_transaction_works(){
        var transactionExpected = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        when(transactionRepository.findById(any(TransactionId.class)))
                .thenReturn(Optional.of(transactionExpected));

        getTarget().store(transactionExpected.getId());

        verify(transactionRepository, times(0))
                .save(any(Transaction.class));

        verify(dailyTransactionRepository, times(1))
                .save(any(DailyTransaction.class));
    }

    @Test
    public void transaction_already_stored_should_do_nothing(){
        var transactionExpected = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        when(dailyTransactionRepository.existsByTransactionId(any(TransactionId.class)))
                .thenReturn(true);

        getTarget().store(transactionExpected.getId());

        verify(transactionRepository, times(0))
                .save(any(Transaction.class));

        verify(dailyTransactionRepository, times(0))
                .save(any(DailyTransaction.class));
    }

    private DailyTransactionReportService getTarget() {
        return new DailyTransactionReportService(transactionRepository, dailyTransactionRepository);
    }
}
