package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.infrastructure.http.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends NotFoundException {
    public TransactionNotFoundException(TransactionId id) {
        super("transaction not found with id  : " + id);
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}

