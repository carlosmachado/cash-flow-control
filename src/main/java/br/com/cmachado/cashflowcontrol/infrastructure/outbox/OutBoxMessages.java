package br.com.cmachado.cashflowcontrol.infrastructure.outbox;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class OutBoxMessages {
    private final OutBoxRepository outBoxRepository;

    public OutBoxMessages(OutBoxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }

    public Message toConsolidateBalance(TransactionId transactionId) {
        var gson = new Gson();
        var json = gson.toJson(new TransactionIdMessage(transactionId));
        return new Message(json);
    }

    public void enqueue(String aggregateId,
                        String aggregate,
                        String operation,
                        Message message) {
        var outbox = OutBox.of(
                aggregateId,
                aggregate,
                operation,
                message
        );
        outBoxRepository.save(outbox);
    }

    @EqualsAndHashCode
    @Getter
    public static class Message implements ValueObject<Message> {
        @NotNull(message = "value is required")
        private String value;

        public Message(String value) {
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
