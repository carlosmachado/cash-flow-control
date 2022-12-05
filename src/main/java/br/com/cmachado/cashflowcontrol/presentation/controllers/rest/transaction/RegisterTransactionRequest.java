package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
