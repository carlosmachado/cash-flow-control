package br.com.cmachado.cashflowcontrol.domain.model.balance;

import br.com.cmachado.cashflowcontrol.domain.model.balance.events.BalanceUpdated;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {
    @Test
    public void credit_or_debit_should_update_balance_amount(){
        var balance = Balance.start();

        assertEquals(Money.ZERO, balance.getAmount());

        var credit = TestTransaction.aTransaction(Transaction.Type.CREDIT, "50.00");

        credit.update(balance);

        assertEquals(Money.of("50.00"), balance.getAmount());

        var debit = TestTransaction.aTransaction(Transaction.Type.DEBIT, "-10.00");

        debit.update(balance);

        assertEquals(Money.of("40.00"), balance.getAmount());
    }

    @Test
    public void update_balance_credit_or_debit_should_register_domain_event(){
        var balance = Balance.start();

        assertEquals(Money.ZERO, balance.getAmount());

        balance.clearEvents();

        assertFalse(
                balance
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof BalanceUpdated)
        );

        var credit = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        credit.update(balance);

        assertEquals(Money.of("100.00"), balance.getAmount());

        assertTrue(
                balance
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof BalanceUpdated)
        );

        balance.clearEvents();

        var debit = TestTransaction.aTransaction(Transaction.Type.DEBIT, "-500.00");

        debit.update(balance);

        assertEquals(Money.of("-400.00"), balance.getAmount());

        assertTrue(
                balance
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof BalanceUpdated)
        );
    }
}
