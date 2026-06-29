package br.com.cmachado.cashflow.consolidation.domain.model;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.shared.money.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceTest {

    @Test
    void starts_at_zero_brl() {
        var balance = Balance.start();
        assertThat(balance.getAmount().getValue()).isEqualByComparingTo("0.00");
        assertThat(balance.getCurrency().toString()).isEqualTo("BRL");
    }

    @Test
    void apply_sums_credits_and_debits() {
        var balance = Balance.start();
        balance.apply(Money.of("100.00"));
        balance.apply(Money.of("-30.00"));
        assertThat(balance.getAmount().getValue()).isEqualByComparingTo("70.00");
    }
}
