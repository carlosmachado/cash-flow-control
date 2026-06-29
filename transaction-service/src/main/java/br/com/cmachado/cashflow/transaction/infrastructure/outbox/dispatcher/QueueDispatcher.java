package br.com.cmachado.cashflow.transaction.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflow.transaction.infrastructure.outbox.OutBox;

public interface QueueDispatcher {
    void dispatch(OutBox outBox);
}
