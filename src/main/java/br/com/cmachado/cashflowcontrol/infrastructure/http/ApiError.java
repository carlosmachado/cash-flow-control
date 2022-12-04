package br.com.cmachado.cashflowcontrol.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    @Getter
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss", locale = "pt-BR", timezone = "${timezone}")
    private final LocalDateTime timestamp;

    @Getter
    private String code;

    @Getter
    private String message;

    @Getter
    private String debugMessage;

    @Getter
    private List<String> errors;

    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, message);
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this(status, message);
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this(status, message, List.of(error));
    }

    public ApiError(HttpStatus status, String code, String message, List<String> errors) {
        this(status, message, errors);
        this.code = code;
    }

    public ApiError(HttpStatus status, String code, String message, String error) {
        this(status, code, message, List.of(error));
    }


    public static ApiError notFound(String message){
        return new ApiError(HttpStatus.NOT_FOUND, message);
    }
}
