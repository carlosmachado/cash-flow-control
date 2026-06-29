package br.com.cmachado.cashflow.shared.http;

import lombok.Getter;

public class ValidationException extends RuntimeException {
    @Getter
    private final String code;
    @Getter
    private final String message;

    public ValidationException(String message) {
        this.message = message;
        this.code = "";
    }

    public ValidationException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
