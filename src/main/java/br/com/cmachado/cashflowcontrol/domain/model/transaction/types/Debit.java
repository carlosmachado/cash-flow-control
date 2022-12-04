package br.com.cmachado.cashflowcontrol.domain.model.transaction.types;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.DebitRegistered;

import javax.persistence.*;


@Entity
@DiscriminatorValue("DEBIT")
public class Debit extends Transaction {

    @Override
    public Transaction.Type type() {
        return Transaction.Type.DEBIT;
    }

    @Override
    public void update(Balance balance) {
        balance.subtract(this);
    }

    protected Debit(){}

    private Debit(TransactionId transactionId,
                  TransactionDate transactionDate,
                  Money amount,
                  String description) {
        super(transactionId, transactionDate, amount, description);
        registerEvent(new DebitRegistered(this));
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
