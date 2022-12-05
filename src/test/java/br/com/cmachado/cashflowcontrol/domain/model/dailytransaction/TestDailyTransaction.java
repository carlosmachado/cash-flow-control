package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;

public class TestDailyTransaction {
    public static DailyTransaction aTransaction(Transaction.Type type, String amount) {
        var transaction = TestTransaction.aTransaction(type, amount);
        return DailyTransaction.store(transaction);
    }
}
