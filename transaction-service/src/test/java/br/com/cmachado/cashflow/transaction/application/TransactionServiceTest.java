package br.com.cmachado.cashflow.transaction.application;

import br.com.cmachado.cashflow.transaction.application.impl.TransactionServiceImpl;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionRepository;
import br.com.cmachado.cashflow.transaction.domain.model.types.Credit;
import br.com.cmachado.cashflow.transaction.domain.model.types.Debit;
import br.com.cmachado.cashflow.transaction.presentation.RegisterTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl service;

    private RegisterTransactionRequest request(Transaction.Type type, String amount) {
        var request = new RegisterTransactionRequest();
        request.setType(type);
        request.setAmount(new BigDecimal(amount));
        request.setTransactionDate(LocalDateTime.now());
        request.setDescription("test");
        return request;
    }

    @Test
    void credit_is_stored_positive() {
        var transaction = service.execute(request(Transaction.Type.CREDIT, "100.00"));

        assertThat(transaction).isInstanceOf(Credit.class);
        assertThat(transaction.getAmount().getValue()).isEqualByComparingTo("100.00");
        verify(transactionRepository).save(transaction);
    }

    @Test
    void debit_is_stored_negative() {
        var transaction = service.execute(request(Transaction.Type.DEBIT, "40.00"));

        assertThat(transaction).isInstanceOf(Debit.class);
        assertThat(transaction.getAmount().getValue()).isEqualByComparingTo("-40.00");
        verify(transactionRepository).save(transaction);
    }
}
