package br.com.cmachado.cashflow.transaction.application.impl;

import br.com.cmachado.cashflow.shared.money.Money;
import br.com.cmachado.cashflow.transaction.application.TransactionService;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionDate;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionId;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionRepository;
import br.com.cmachado.cashflow.transaction.presentation.RegisterTransactionRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transaction execute(RegisterTransactionRequest request) {
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
