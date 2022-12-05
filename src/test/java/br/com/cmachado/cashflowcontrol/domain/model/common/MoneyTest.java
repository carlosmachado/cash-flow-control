package br.com.cmachado.cashflowcontrol.domain.model.common;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.common.money.MoneyInvalidException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    @Test
    public void can_create_money(){
        assertThat(Money.of(new BigDecimal("1.00")).getValue()).isEqualTo(new BigDecimal("1.00"));
        assertThat(Money.of(new BigDecimal("-1.00")).getValue()).isEqualTo(new BigDecimal("-1.00"));
    }

    @Test
    public void scale_not_configured(){
        var value = new BigDecimal("123.12");

        Assert.assertEquals(2, value.scale());
    }

    @Test
    public void force_money_scale_2(){
        assertThrows(MoneyInvalidException.class, () -> Money.of(new BigDecimal(9391.72)));
    }

    @Test
    public void equals_verifies_value_not_instance() {
        var money1 = Money.of("5.00");
        var money2 = Money.of("5.00");

        assertTrue(money1.equals(money2));
        assertTrue(money1.sameValueAs(money2));
    }
}
