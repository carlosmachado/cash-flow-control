package br.com.cmachado.cashflow.transaction.domain.model;

import br.com.cmachado.cashflow.shared.http.NotFoundException;
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
