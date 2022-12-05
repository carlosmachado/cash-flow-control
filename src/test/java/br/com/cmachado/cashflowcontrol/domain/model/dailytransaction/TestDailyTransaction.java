package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDailyTransaction {
    public static DailyTransaction aTransaction(Transaction.Type type, String amount) {
        var transaction = TestTransaction.aTransaction(type, amount);
        return DailyTransaction.store(transaction);
    }

    public static List<DailyTransaction> someTransactionsForDate(LocalDate date, int count) {
        var list = new ArrayList<DailyTransaction>(count);

        for (var i=1; i<=count; i++){
            var type = i % 2 == 0 ? Transaction.Type.CREDIT : Transaction.Type.DEBIT;
            var amount = Money.of("10").multiply(i).toString();
            list.add(aTransaction(type, amount));
        }

        return list;
    }
}
