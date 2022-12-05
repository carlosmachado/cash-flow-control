package br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBox;

public class TransactionQueueDispatcher implements QueueDispatcher {
    private final static String BALANCE_QUEUE = "balance_update";
    private final static String DAILY_BALANCE_QUEUE = "daily_balance_update";

    @Override
    public void dispatch(OutBox outBox) {

    }
}
