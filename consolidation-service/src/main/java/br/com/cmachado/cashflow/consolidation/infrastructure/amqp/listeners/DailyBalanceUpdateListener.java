package br.com.cmachado.cashflow.consolidation.infrastructure.amqp.listeners;

import br.com.cmachado.cashflow.consolidation.domain.service.DailyTransactionReportService;
import br.com.cmachado.cashflow.consolidation.infrastructure.amqp.AMQPConfig;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.json.JsonSupport;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DailyBalanceUpdateListener {
    private final DailyTransactionReportService dailyTransactionReportService;

    public DailyBalanceUpdateListener(DailyTransactionReportService dailyTransactionReportService) {
        this.dailyTransactionReportService = dailyTransactionReportService;
    }

    @RabbitListener(queues = AMQPConfig.DAILY_BALANCE_QUEUE)
    public void listen(String in) {
        var message = JsonSupport.gson().fromJson(in, TransactionRegisteredMessage.class);

        dailyTransactionReportService.store(message);
    }
}
