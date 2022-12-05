package br.com.cmachado.cashflowcontrol.domain.service.dailybalance;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionNotFoundException;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@DomainService
@Service
public class DailyBalanceReportService {
    private final TransactionRepository transactionRepository;
    private final DailyTransactionRepository dailyTransactionRepository;

    public DailyBalanceReportService(TransactionRepository transactionRepository,
                                     DailyTransactionRepository dailyTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.dailyTransactionRepository = dailyTransactionRepository;
    }

    @Transactional
    public void store(TransactionId transactionId) {
        if (dailyTransactionRepository.existsByTransactionId(transactionId))
            return;

        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        var dailyBalance = DailyTransaction.store(transaction);

        dailyTransactionRepository.save(dailyBalance);
    }
}
