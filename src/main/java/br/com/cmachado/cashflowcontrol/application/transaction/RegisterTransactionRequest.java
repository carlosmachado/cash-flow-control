package br.com.cmachado.cashflowcontrol.application.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegisterTransactionRequest {
    private LocalDateTime transactionDate;
    private Transaction.Type type;
    private BigDecimal amount;
    private String description;
}
