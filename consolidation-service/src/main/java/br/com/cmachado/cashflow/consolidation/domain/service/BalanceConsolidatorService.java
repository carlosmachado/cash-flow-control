package br.com.cmachado.cashflow.consolidation.domain.service;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.AppliedTransaction;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.AppliedTransactionRepository;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceId;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.ddd.DomainService;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@DomainService
@Service
public class BalanceConsolidatorService {
    private final BalanceRepository balanceRepository;
    private final AppliedTransactionRepository appliedTransactionRepository;

    public BalanceConsolidatorService(BalanceRepository balanceRepository,
                                      AppliedTransactionRepository appliedTransactionRepository) {
        this.balanceRepository = balanceRepository;
        this.appliedTransactionRepository = appliedTransactionRepository;
    }

    @Transactional
    public void consolidate(TransactionRegisteredMessage message) {
        var transactionId = UUID.fromString(message.getTransactionId());

        // lock the balance row first, then guard against duplicate delivery
        var balance = balanceRepository.findByIdForUpdate(BalanceId.singleton())
                .orElseGet(Balance::start);

        if (appliedTransactionRepository.existsById(transactionId))
            return;

        balance.apply(Money.of(message.getAmount()));

        balanceRepository.save(balance);
        appliedTransactionRepository.save(new AppliedTransaction(transactionId));
    }
}
