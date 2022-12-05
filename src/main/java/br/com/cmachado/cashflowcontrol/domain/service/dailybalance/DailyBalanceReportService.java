package br.com.cmachado.cashflowcontrol.domain.service.dailybalance;

import br.com.cmachado.cashflowcontrol.domain.model.dailybalance.DailyBalance;
import br.com.cmachado.cashflowcontrol.domain.model.dailybalance.DailyBalanceRepository;
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
    private final DailyBalanceRepository dailyBalanceRepository;

    public DailyBalanceReportService(TransactionRepository transactionRepository,
                                     DailyBalanceRepository dailyBalanceRepository) {
        this.transactionRepository = transactionRepository;
        this.dailyBalanceRepository = dailyBalanceRepository;
    }

    @Transactional
    public void store(TransactionId transactionId) {
        if (dailyBalanceRepository.existsByTransactionId(transactionId))
            return;

        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        var dailyBalance = DailyBalance.store(transaction);

        dailyBalanceRepository.save(dailyBalance);
    }
}
