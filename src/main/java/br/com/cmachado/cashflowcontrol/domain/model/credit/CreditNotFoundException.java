package br.com.cmachado.cashflowcontrol.domain.model.credit;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.infrastructure.http.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CreditNotFoundException extends NotFoundException {
    public CreditNotFoundException(TransactionId id) {
        super("credit not found with id  : " + id);
    }

    public CreditNotFoundException(String message) {
        super(message);
    }
}

