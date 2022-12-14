package br.com.cmachado.cashflowcontrol.domain.service.balance;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionNotFoundException;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.Semaphore;

@DomainService
@Service
public class BalanceConsolidatorService {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private Semaphore mutex = new Semaphore(1);

    public BalanceConsolidatorService(TransactionRepository transactionRepository,
                                      BalanceRepository balanceRepository) {
        this.transactionRepository = transactionRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public void consolidate(TransactionId transactionId) throws InterruptedException {
        try{
            mutex.acquire();

            var transaction = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new TransactionNotFoundException(transactionId));

            var balance = getBalance();

            transaction.update(balance);

            balanceRepository.save(balance);
        } finally {
            mutex.release();
        }
    }

    public Balance getBalance() {
        var balances = balanceRepository.findAll();
        if (balances.isEmpty())
            return Balance.start();
        return balances.get(0);
    }
}
