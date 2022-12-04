package br.com.cmachado.cashflowcontrol.domain.model.common.money;

import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Currency implements ValueObject<Currency> {
    public static final Currency BRL = new Currency("BRL");

    private final String code;

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
