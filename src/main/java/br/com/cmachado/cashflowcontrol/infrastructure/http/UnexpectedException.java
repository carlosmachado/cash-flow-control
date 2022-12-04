package br.com.cmachado.cashflowcontrol.infrastructure.http;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class UnexpectedException extends RuntimeException {
    @Getter
    private final List<String> errors;

    public UnexpectedException(List<String> errors) {
        this.errors = errors;
    }

    public UnexpectedException(String... errors) {
        this.errors = Arrays.asList(errors);
    }

    public String getMessage() {
        return this.errors.toString();
    }
}
