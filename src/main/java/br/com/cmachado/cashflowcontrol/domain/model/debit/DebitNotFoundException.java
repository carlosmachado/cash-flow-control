package br.com.cmachado.cashflowcontrol.domain.model.debit;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.infrastructure.http.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DebitNotFoundException extends NotFoundException {
    public DebitNotFoundException(TransactionId id) {
        super("debit not found with id  : " + id);
    }

    public DebitNotFoundException(String message) {
        super(message);
    }
}

