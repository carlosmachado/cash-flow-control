package br.com.cmachado.cashflow.transaction.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
public class QueueDispatcherFactory {
    private final BeanFactory beanFactory;

    public QueueDispatcherFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public QueueDispatcher dispatcherFor(String aggregate) {
        switch (aggregate) {
            case Transaction.TRANSACTION_AGGREGATE:
                return beanFactory.getBean(TransactionQueueDispatcher.class);
            default:
                throw new IllegalArgumentException("dispatcher not found for aggregate: " + aggregate);
        }
    }
}
