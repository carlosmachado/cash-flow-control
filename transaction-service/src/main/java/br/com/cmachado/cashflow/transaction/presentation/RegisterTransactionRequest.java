package br.com.cmachado.cashflow.transaction.presentation;

import br.com.cmachado.cashflow.transaction.domain.model.Transaction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegisterTransactionRequest {
    @NotNull
    private LocalDateTime transactionDate;
    @NotNull
    private Transaction.Type type;
    @NotNull
    private BigDecimal amount;
    @NotEmpty
    private String description;
}
