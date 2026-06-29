package br.com.cmachado.cashflow.consolidation.presentation.balance;

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
