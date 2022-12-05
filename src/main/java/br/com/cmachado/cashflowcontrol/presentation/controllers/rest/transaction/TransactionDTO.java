package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction;

import lombok.Data;

import java.math.BigDecimal;

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
