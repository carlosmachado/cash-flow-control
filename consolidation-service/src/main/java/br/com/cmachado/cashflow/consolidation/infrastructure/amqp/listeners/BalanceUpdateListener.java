package br.com.cmachado.cashflow.consolidation.infrastructure.amqp.listeners;

import br.com.cmachado.cashflow.consolidation.domain.service.BalanceConsolidatorService;
import br.com.cmachado.cashflow.consolidation.infrastructure.amqp.AMQPConfig;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.json.JsonSupport;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BalanceUpdateListener {
    private final BalanceConsolidatorService balanceConsolidatorService;

    public BalanceUpdateListener(BalanceConsolidatorService balanceConsolidatorService) {
        this.balanceConsolidatorService = balanceConsolidatorService;
    }

    @RabbitListener(queues = AMQPConfig.BALANCE_QUEUE)
    public void listen(String in) {
        var message = JsonSupport.gson().fromJson(in, TransactionRegisteredMessage.class);

        balanceConsolidatorService.consolidate(message);
    }
}
