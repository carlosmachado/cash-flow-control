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
import org.springframework.format.annotation.NumberFormat;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Monetary value object. Persisted as a plain {@code BigDecimal} column, but all
 * arithmetic delegates to JavaMoney (JSR-354 / Moneta) using {@link Currency#CODE}.
 */
@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money implements ValueObject<Money> {
    @Embedded
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    @NotNull(message = "value is required")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(name = "amount", nullable = false, scale = 2)
    private BigDecimal value;

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

    private static Money of(MonetaryAmount amount) {
        return of(amount.getNumber().numberValue(BigDecimal.class));
    }

    private MonetaryAmount toMonetary() {
        return org.javamoney.moneta.Money.of(value, Currency.CODE);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public boolean sameValueAs(Money other) {
        return other != null && this.value.compareTo(other.getValue()) == 0;
    }

    public Money sum(Money money) {
        return of(toMonetary().add(money.toMonetary()));
    }

    public static Money sum(Money... moneys) {
        var sum = Money.ZERO;

        for (var money : moneys)
            sum = sum.sum(money);

        return sum;
    }

    public Money subtract(Money money) {
        return of(toMonetary().subtract(money.toMonetary()));
    }

    public static boolean isNegative(Money money) {
        return money.toMonetary().isNegative();
    }

    public Money multiply(int value) {
        return of(toMonetary().multiply(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
