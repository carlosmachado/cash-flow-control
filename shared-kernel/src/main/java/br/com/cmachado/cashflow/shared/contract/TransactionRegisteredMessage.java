package br.com.cmachado.cashflow.shared.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Integration contract published by transaction-service and consumed by
 * consolidation-service. Event-carried state transfer: it carries the full
 * transaction state so the consumer never has to read the producer's database.
 *
 * {@code amount} is already signed (credit positive, debit negative).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRegisteredMessage {
    private String transactionId;
    private String type;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime transactionDate;
}
