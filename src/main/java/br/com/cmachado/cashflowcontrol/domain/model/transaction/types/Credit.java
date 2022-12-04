package br.com.cmachado.cashflowcontrol.domain.model.transaction.types;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.events.CreditRegistered;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("CREDIT")
public class Credit extends Transaction {

    protected Credit(){}

    @Override
    public Type type() {
        return Type.CREDIT;
    }

    @Override
    public void update(Balance balance) {
        balance.sum(this);
    }

    private Credit(TransactionId transactionId,
                   TransactionDate transactionDate,
                   Money amount,
                   String description) {
        super(transactionId, transactionDate, amount, description);
        registerEvent(new CreditRegistered(this));
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
