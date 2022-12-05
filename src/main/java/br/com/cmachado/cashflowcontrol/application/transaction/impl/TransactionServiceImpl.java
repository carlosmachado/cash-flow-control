package br.com.cmachado.cashflowcontrol.application.transaction.impl;

import br.com.cmachado.cashflowcontrol.application.transaction.TransactionService;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.shared.ApplicationService;
import br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction.RegisterTransactionRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transaction execute(RegisterTransactionRequest request){
        var type = request.getType();
        var transactionDate = TransactionDate.was(request.getTransactionDate());
        var amount = Money.of(request.getAmount());
        var description = request.getDescription();
        var transactionId = TransactionId.generate();

        var transaction = type.register(
                transactionId,
                transactionDate,
                amount,
                description
        );

        transactionRepository.save(transaction);

        return transaction;
    }
}
