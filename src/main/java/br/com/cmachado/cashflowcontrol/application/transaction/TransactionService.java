package br.com.cmachado.cashflowcontrol.application.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.shared.ApplicationService;
import br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction.RegisterTransactionRequest;

import javax.transaction.Transactional;

@ApplicationService
public interface TransactionService {
    @Transactional
    Transaction execute(RegisterTransactionRequest request);
}
