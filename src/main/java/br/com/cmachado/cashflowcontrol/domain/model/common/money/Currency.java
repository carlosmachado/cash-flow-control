package br.com.cmachado.cashflowcontrol.domain.model.common.money;

import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@Embeddable
@Getter
public class Currency implements ValueObject<Currency> {
    @Embedded
    public static final Currency BRL = new Currency("BRL");

    @NotNull(message = "code is required")
    @Column(name = "currency", nullable = false, length = 10)
    private String code;

    protected Currency(){}

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
