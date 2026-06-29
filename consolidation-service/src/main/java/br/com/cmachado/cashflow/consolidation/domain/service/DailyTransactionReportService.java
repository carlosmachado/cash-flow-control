package br.com.cmachado.cashflow.consolidation.domain.service;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransactionRepository;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.ddd.DomainService;
import br.com.cmachado.cashflow.shared.money.Currency;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@DomainService
@Service
public class DailyTransactionReportService {
    private final DailyTransactionRepository dailyTransactionRepository;

    public DailyTransactionReportService(DailyTransactionRepository dailyTransactionRepository) {
        this.dailyTransactionRepository = dailyTransactionRepository;
    }

    @Transactional
    public void store(TransactionRegisteredMessage message) {
        var transactionId = UUID.fromString(message.getTransactionId());

        if (dailyTransactionRepository.existsByTransactionId(transactionId))
            return;

        var dailyTransaction = DailyTransaction.store(
                transactionId,
                message.getTransactionDate(),
                Currency.BRL,
                Money.of(message.getAmount())
        );

        dailyTransactionRepository.save(dailyTransaction);
    }
}
