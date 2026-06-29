package br.com.cmachado.cashflow.transaction.domain.model.types;

import br.com.cmachado.cashflow.shared.money.Money;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionDate;
import br.com.cmachado.cashflow.transaction.domain.model.TransactionId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DEBIT")
public class Debit extends Transaction {

    @Override
    public Transaction.Type type() {
        return Transaction.Type.DEBIT;
    }

    protected Debit() {
    }

    private Debit(TransactionId transactionId,
                  TransactionDate transactionDate,
                  Money amount,
                  String description) {
        super(transactionId, transactionDate, amount, description);
    }

    public static Debit register(TransactionId transactionId,
                                 TransactionDate transactionDate,
                                 Money amount,
                                 String description) {
        if (!Money.isNegative(amount))
            throw new IllegalArgumentException("amount must be negative");

        return new Debit(
                transactionId,
                transactionDate,
                amount,
                description
        );
    }
}
