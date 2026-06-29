package br.com.cmachado.cashflow.shared.http;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class BadRequestException extends RuntimeException {
    @Getter
    private final List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }

    public BadRequestException(String... errors) {
        this.errors = Arrays.asList(errors);
    }

    @Override
    public String getMessage() {
        return this.errors.toString();
    }
}
