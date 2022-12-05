package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.TransactionRegistered;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTest {
    @Test
    public void register_credit_or_debit_should_register_domain_event(){
        var credit = TestTransaction.aTransaction(Transaction.Type.CREDIT, "100.00");

        assertTrue(
                credit
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof TransactionRegistered)
        );

        var debit = TestTransaction.aTransaction(Transaction.Type.DEBIT, "-50.00");

        assertTrue(
                debit
                        .getUncommitedEvents()
                        .stream()
                        .anyMatch(x -> x instanceof TransactionRegistered)
        );
    }
}
