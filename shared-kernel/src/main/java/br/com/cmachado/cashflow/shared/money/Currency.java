package br.com.cmachado.cashflow.shared.money;

import br.com.cmachado.cashflow.shared.ddd.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Currency implements ValueObject<Currency> {
    public static final String CODE = "BRL";

    @Embedded
    public static final Currency BRL = new Currency(CODE);

    @NotNull(message = "code is required")
    @Column(name = "currency", nullable = false, length = 10)
    private String code;

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
