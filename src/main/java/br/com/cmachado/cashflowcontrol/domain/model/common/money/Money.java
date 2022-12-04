package br.com.cmachado.cashflowcontrol.domain.model.common.money;

import br.com.cmachado.cashflowcontrol.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@EqualsAndHashCode
@Embeddable
@Getter
public class Money implements ValueObject<Money> {
    @Embedded
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    @NotNull(message = "money is required")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(name = "value", nullable = false, scale = 2)
    private BigDecimal value;

    protected Money() {
    }

    private Money(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isValid(BigDecimal value) {
        return value != null && value.scale() <= 2;
    }

    public static Money of(String value) {
        return of(new BigDecimal(value));
    }

    public static Money of(BigDecimal value) {
        if (!isValid(value))
            throw new MoneyInvalidException(value);

        return new Money(value);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public boolean sameValueAs(Money other) {
        return other != null && this.value.compareTo(other.getValue()) == 0;
    }

    public Money sum(Money money) {
        return Money.of(this.value.add(money.value));
    }

    public static Money sum(Money... moneys) {
        var sum = Money.of(BigDecimal.ZERO);

        for (var money : moneys)
            sum = sum.sum(money);

        return sum;
    }

    public Money subtract(Money money) {
        var subtract = this.value.subtract(money.value);
        return Money.of(subtract);
    }

    public static boolean isNegative(Money money) {
        return money.value.longValue() < 0;
    }

    public boolean greaterThanZero() {
        return value.compareTo(new BigDecimal(0)) > 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String formatted() {
        return "R$ " + value.toString();
    }
}

