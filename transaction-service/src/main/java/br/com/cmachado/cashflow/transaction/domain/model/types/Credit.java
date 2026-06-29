package br.com.cmachado.cashflow.transaction.domain.model.types;

import br.com.cmachado.cashflow.shared.money.Money;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionDate;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CREDIT")
public class Credit extends Transaction {

    protected Credit() {
    }

    @Override
    public Type type() {
        return Type.CREDIT;
    }

    private Credit(TransactionId transactionId,
                   TransactionDate transactionDate,
                   Money amount,
                   String description) {
        super(transactionId, transactionDate, amount, description);
    }

    public static Credit register(TransactionId transactionId,
                                  TransactionDate transactionDate,
                                  Money amount,
                                  String description) {
        if (Money.isNegative(amount))
            throw new IllegalArgumentException("amount must be positive");

        return new Credit(
                transactionId,
                transactionDate,
                amount,
                description
        );
    }
}
