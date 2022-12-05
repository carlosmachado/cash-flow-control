package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.events.DailyTransactionStored;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.TransactionRegistered;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DailyTransactionTest {
    @Test
    public void store_credit_or_debit_should_register_domain_event(){
        var credit = TestDailyTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        assertTrue(
                credit
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof DailyTransactionStored)
        );

        var debit = TestDailyTransaction.aTransaction(Transaction.Type.DEBIT, "-50.00");

        assertTrue(
                debit
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof DailyTransactionStored)
        );
    }
}
