package br.com.cmachado.cashflow.consolidation.infrastructure.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {
    public static final String BALANCE_QUEUE = "balance_update";
    public static final String DAILY_BALANCE_QUEUE = "daily_balance_update";

    @Bean
    public Queue balanceQueue() {
        return new Queue(BALANCE_QUEUE, true);
    }

    @Bean
    public Queue dailyBalanceQueue() {
        return new Queue(DAILY_BALANCE_QUEUE, true);
    }
}
