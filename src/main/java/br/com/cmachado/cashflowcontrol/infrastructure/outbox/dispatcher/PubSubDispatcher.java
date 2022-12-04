package br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBox;

public interface PubSubDispatcher {
    void dispatch(OutBox outBox);
}
