package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.balance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String currency;
    private BigDecimal amount;
}
