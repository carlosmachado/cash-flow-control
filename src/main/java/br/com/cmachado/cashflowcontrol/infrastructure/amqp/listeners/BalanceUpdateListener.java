package br.com.cmachado.cashflowcontrol.infrastructure.amqp.listeners;

import br.com.cmachado.cashflowcontrol.domain.service.balance.BalanceConsolidatorService;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.TransactionIdMessage;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher.TransactionQueueDispatcher;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BalanceUpdateListener {
    private final BalanceConsolidatorService balanceConsolidatorService;

    public BalanceUpdateListener(BalanceConsolidatorService balanceConsolidatorService) {
        this.balanceConsolidatorService = balanceConsolidatorService;
    }

    @RabbitListener(queues = TransactionQueueDispatcher.BALANCE_QUEUE)
    public void listen(String in) throws InterruptedException {
        var gson = new Gson();

        var transactionId = gson.fromJson(in, TransactionIdMessage.class).getTransactionId();

        balanceConsolidatorService.consolidate(transactionId);
    }
}
