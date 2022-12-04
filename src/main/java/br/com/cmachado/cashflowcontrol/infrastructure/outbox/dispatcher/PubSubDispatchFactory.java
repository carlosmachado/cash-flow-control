package br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
public class PubSubDispatchFactory {
    private final BeanFactory beanFactory;

    public PubSubDispatchFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public PubSubDispatcher dispatcherFor(String aggregate) {
        switch (aggregate) {
//            case "TRANSACTION":
//                return beanFactory.getBean(CommercialOrderPubSubDispatcher.class);
//            case "BALANCE":
//                return beanFactory.getBean(OrderPubSubDispatcher.class);
            default:
                throw new IllegalArgumentException("dispatcher not found for aggregate: " + aggregate);
        }
    }
}
