package br.com.cmachado.cashflow.shared.money;

import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Embeddable
@Getter
public class Currency implements ValueObject<Currency> {
    @Embedded
    public static final Currency BRL = new Currency("BRL");

    @NotNull(message = "code is required")
    @Column(name = "currency", nullable = false, length = 10)
    private String code;

    protected Currency() {
    }

    private Currency(String code) {
        this.code = code;
    }

    @Override
    public boolean sameValueAs(Currency other) {
        return other != null && other.equals(this);
    }

    @Override
    public String toString() {
        return code;
    }
}
