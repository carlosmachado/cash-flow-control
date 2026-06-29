package br.com.cmachado.cashflow.transaction.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBox;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueDispatcher implements QueueDispatcher {
    public static final String BALANCE_QUEUE = "balance_update";
    public static final String DAILY_BALANCE_QUEUE = "daily_balance_update";

    private final RabbitTemplate rabbitTemplate;

    public TransactionQueueDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dispatch(OutBox outBox) {
        rabbitTemplate.convertAndSend(BALANCE_QUEUE, outBox.getMessage());
        rabbitTemplate.convertAndSend(DAILY_BALANCE_QUEUE, outBox.getMessage());
    }
}
