package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.dailytransaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyTransactionDTO {
    private String id;
    private String createdAt;
    private String currency;
    private BigDecimal amount;
    private String transactionId;
    private String transactionDate;
    private String date;
}
