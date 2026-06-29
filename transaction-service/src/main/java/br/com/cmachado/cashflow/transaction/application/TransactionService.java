package br.com.cmachado.cashflow.transaction.application;

import br.com.cmachado.cashflow.shared.ddd.ApplicationService;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.presentation.RegisterTransactionRequest;
import jakarta.transaction.Transactional;

@ApplicationService
public interface TransactionService {
    @Transactional
    Transaction execute(RegisterTransactionRequest request);
}
