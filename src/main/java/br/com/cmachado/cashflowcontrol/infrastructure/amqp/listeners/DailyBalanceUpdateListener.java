package br.com.cmachado.cashflowcontrol.infrastructure.amqp.listeners;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionNotFoundException;
import br.com.cmachado.cashflowcontrol.domain.service.dailybalance.DailyTransactionReportService;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.TransactionIdMessage;
import br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher.TransactionQueueDispatcher;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DailyBalanceUpdateListener {
    static final Logger logger = LoggerFactory.getLogger(DailyBalanceUpdateListener.class);

    private final DailyTransactionReportService dailyTransactionReportService;

    public DailyBalanceUpdateListener(DailyTransactionReportService dailyTransactionReportService) {
        this.dailyTransactionReportService = dailyTransactionReportService;
    }

    @RabbitListener(queues = TransactionQueueDispatcher.DAILY_BALANCE_QUEUE)
    public void listen(String in) {
        var gson = new Gson();

        var transactionId = gson.fromJson(in, TransactionIdMessage.class).getTransactionId();

        try{
            dailyTransactionReportService.store(transactionId);
        }catch (TransactionNotFoundException e){
            logger.error("fail store daily balance transaction not found " + e.getMessage(), e);
        }
    }
}
