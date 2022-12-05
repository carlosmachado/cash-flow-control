package br.com.cmachado.cashflowcontrol.application.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction.RegisterTransactionRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    private RegisterTransactionRequest request;

    @BeforeEach
    public void prepare(){
        request = new RegisterTransactionRequest();
        request.setTransactionDate(LocalDateTime.now());
        request.setDescription("transaction test");
    }

    @Test
    public void register_a_credit_transaction_should_save_in_repo(){
        var type = Transaction.Type.CREDIT;
        var amount = "100.00";
        var transactionExpected = TestTransaction.aTransaction(type, amount);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transactionExpected);

        request.setType(type);
        request.setAmount(new BigDecimal(amount));

        var transaction = getTarget().execute(request);

        verify(transactionRepository, times(1))
                .save(any(Transaction.class));

        assertEquals("types not credit", transactionExpected.getType(), transaction.getType());
        assertEquals("amounts different", transactionExpected.getAmount(), transaction.getAmount());
    }

    @NotNull
    private TransactionService getTarget() {
        return new TransactionService(transactionRepository);
    }

    @Test
    public void register_a_debit_transaction_should_save_in_repo(){
        var type = Transaction.Type.DEBIT;
        var amount = "-90.00";
        var transactionExpected = TestTransaction.aTransaction(type, amount);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transactionExpected);

        request.setType(type);
        request.setAmount(new BigDecimal(amount));

        var transaction = getTarget().execute(request);

        verify(transactionRepository, times(1))
                .save(any(Transaction.class));

        assertEquals("types not credit", transactionExpected.getType(), transaction.getType());
        assertEquals("amounts different", transactionExpected.getAmount(), transaction.getAmount());
    }
}
