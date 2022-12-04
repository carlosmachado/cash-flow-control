package br.com.cmachado.cashflowcontrol.domain.model.common.money;

import br.com.cmachado.cashflowcontrol.infrastructure.http.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MoneyInvalidException extends BadRequestException {
    public MoneyInvalidException(BigDecimal value) {
        super("Value is not a valid money : " + value);
    }
}

