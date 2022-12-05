package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;

import java.time.LocalDateTime;

public class TestTransaction {
    public static Transaction aTransaction(Transaction.Type type, String amount) {
        return type.register(
                TransactionId.generate(),
                TransactionDate.was(LocalDateTime.now()),
                Money.of(amount),
                "some " + type
        );
    }
}
