package br.com.cmachado.cashflow.transaction.infrastructure.outbox;

import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import br.com.cmachado.cashflow.shared.json.JsonSupport;
import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class OutBoxMessages {
    private final OutBoxRepository outBoxRepository;

    public OutBoxMessages(OutBoxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }

    public Message toRegisteredMessage(Transaction transaction) {
        var payload = new TransactionRegisteredMessage(
                transaction.getId().toString(),
                transaction.getType(),
                transaction.getAmount().getValue(),
                transaction.getCurrency().toString(),
                transaction.getTransactionDate().getValue()
        );
        return new Message(JsonSupport.gson().toJson(payload));
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
        private final String value;

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
