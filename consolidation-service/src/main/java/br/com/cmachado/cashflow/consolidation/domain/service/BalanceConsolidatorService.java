package br.com.cmachado.cashflow.consolidation.domain.service;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceId;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.ddd.DomainService;
import br.com.cmachado.cashflow.shared.money.Money;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@DomainService
@Service
public class BalanceConsolidatorService {
    private final BalanceRepository balanceRepository;

    public BalanceConsolidatorService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public void consolidate(TransactionRegisteredMessage message) {
        var balance = balanceRepository.findByIdForUpdate(BalanceId.singleton())
                .orElseGet(Balance::start);

        balance.apply(Money.of(message.getAmount()));

        balanceRepository.save(balance);
    }
}
