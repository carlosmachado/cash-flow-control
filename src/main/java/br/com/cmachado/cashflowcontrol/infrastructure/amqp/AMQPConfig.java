package br.com.cmachado.cashflowcontrol.infrastructure.amqp;

import br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher.TransactionQueueDispatcher;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {
    @Bean
    public Queue balanceQueue() {
        return new Queue(TransactionQueueDispatcher.BALANCE_QUEUE, false);
    }

    @Bean
    public Queue dailyBalanceQueue() {
        return new Queue(TransactionQueueDispatcher.DAILY_BALANCE_QUEUE, false);
    }
}
