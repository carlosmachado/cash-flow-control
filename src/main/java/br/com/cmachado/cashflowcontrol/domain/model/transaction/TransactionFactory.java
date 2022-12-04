package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;

public interface TransactionFactory {
    Transaction register(TransactionId transactionId,
                         TransactionDate transactionDate,
                         Money amount,
                         String description);
}
