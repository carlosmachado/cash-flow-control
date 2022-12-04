package br.com.cmachado.cashflowcontrol.domain.service.balance;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import br.com.cmachado.cashflowcontrol.domain.model.credit.CreditRepository;
import br.com.cmachado.cashflowcontrol.domain.model.debit.Debit;
import br.com.cmachado.cashflowcontrol.domain.model.debit.DebitRepository;
import br.com.cmachado.cashflowcontrol.domain.shared.DomainService;

import javax.transaction.Transactional;
import java.util.concurrent.Semaphore;

@DomainService
public class BalanceConsolidatorService {
    private final CreditRepository creditRepository;
    private final DebitRepository debitRepository;
    private final BalanceRepository balanceRepository;
    private Semaphore mutex;

    public BalanceConsolidatorService(CreditRepository creditRepository,
                                      DebitRepository debitRepository,
                                      BalanceRepository balanceRepository) {
        this.creditRepository = creditRepository;
        this.debitRepository = debitRepository;
        this.balanceRepository = balanceRepository;
        mutex = new Semaphore(1);
    }

    public void consolidate(TransactionId transactionId) throws InterruptedException {
        var isADebit = debitRepository.findById(transactionId);

        if (isADebit.isPresent()){
            consolidateDebit(isADebit.get());
            return;
        }

        var isACredit = creditRepository.findById(transactionId);
        if (isACredit.isPresent()){
            consolidateCredit(isACredit.get());
            return;
        }

        throw new IllegalArgumentException("credit/debit not found transaction id " + transactionId);
    }

    @Transactional
    public void consolidateCredit(Credit credit) throws InterruptedException {
        mutex.acquire();

        var balance = getBalance();

        balance.sum(credit);

        balanceRepository.save(balance);

        mutex.release();
    }

    public Balance getBalance() {
        var balances = balanceRepository.findAll();
        if (balances.isEmpty())
            return Balance.start();
        return balances.get(0);
    }

    @Transactional
    public void consolidateDebit(Debit debit) throws InterruptedException {
        mutex.acquire();

        var balance = getBalance();

        balance.subtract(debit);

        balanceRepository.save(balance);

        mutex.release();
    }
}
