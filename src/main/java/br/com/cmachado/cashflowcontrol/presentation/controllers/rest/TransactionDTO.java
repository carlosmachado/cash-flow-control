package br.com.cmachado.cashflowcontrol.presentation.controllers.rest;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {
    private String id;
    private String createdAt;
    private String transactionDate;
    private String type;
    private String currency;
    private BigDecimal amount;
    private String description;
}
