package br.com.cmachado.cashflow.transaction.domain.model;

import br.com.cmachado.cashflow.shared.money.Money;

public interface TransactionFactory {
    Transaction register(TransactionId transactionId,
                         TransactionDate transactionDate,
                         Money amount,
                         String description);
}
