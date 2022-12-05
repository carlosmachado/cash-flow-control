package br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBox;

public interface QueueDispatcher {
    void dispatch(OutBox outBox);
}
