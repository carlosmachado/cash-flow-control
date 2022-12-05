package br.com.cmachado.cashflowcontrol.domain.service;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.service.balance.BalanceConsolidatorService;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceConsolidatorServiceTest {
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void consolidate_credit_works() throws InterruptedException {
        var transactionExpected = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        when(balanceRepository.findAll())
                .thenReturn(List.of(Balance.start()));

        when(transactionRepository.findById(any(TransactionId.class)))
                .thenReturn(Optional.of(transactionExpected));

        getTarget().consolidate(transactionExpected.getId());

        verify(transactionRepository, times(0))
                .save(any(Transaction.class));

        verify(balanceRepository, times(1))
                .save(any(Balance.class));
    }

    @Test
    public void can_only_consolidate_1_transaction_per_time() {
        thrown.expect(RuntimeException.class);

        var transactionExpected = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        when(balanceRepository.findAll())
                .thenReturn(List.of(Balance.start()));

        when(transactionRepository.findById(any(TransactionId.class)))
                .thenReturn(Optional.of(transactionExpected));

        var target = getTarget();

        int count = 2;
        var executorService = Executors.newFixedThreadPool(count);
        IntStream.range(1, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        target.consolidate(transactionExpected.getId());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }));
        executorService.shutdown();
    }

    @NotNull
    private BalanceConsolidatorService getTarget() {
        return new BalanceConsolidatorService(transactionRepository, balanceRepository);
    }
}
