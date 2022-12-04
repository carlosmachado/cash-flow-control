package br.com.cmachado.cashflowcontrol.application.credit;

import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegisterCreditRequest {
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private String description;
    private Credit.Type type;
}
