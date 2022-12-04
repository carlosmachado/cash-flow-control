package br.com.cmachado.cashflowcontrol.infrastructure.outbox;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class OutBoxMessages {

    public Message toConsolidateBalance(TransactionId transactionId) {
//        return new Message(serializer.toJson(new CommercialOrderIdMessage(commercialOrderId)));
        return null;
    }

    @EqualsAndHashCode
    @Getter
    public static class Message implements ValueObject<Message> {
        @NotNull(message = "value is required")
        private String value;

        private Message(String value) {
            this.value = value;
        }

        @Override
        public boolean sameValueAs(Message other) {
            return other != null && other.equals(this);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
