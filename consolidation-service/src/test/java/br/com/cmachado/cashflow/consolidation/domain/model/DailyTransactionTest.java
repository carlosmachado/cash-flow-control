package br.com.cmachado.cashflow.consolidation.domain.model;

import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.ConsolidatedAmount;
import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflow.shared.money.Currency;
import br.com.cmachado.cashflow.shared.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DailyTransactionTest {

    private DailyTransaction store(String amount) {
        return DailyTransaction.store(UUID.randomUUID(), LocalDateTime.now(), Currency.BRL, Money.of(amount));
    }

    @Test
    void derives_date_from_transaction_date() {
        var date = LocalDateTime.of(2025, 1, 15, 10, 0);
        var daily = DailyTransaction.store(UUID.randomUUID(), date, Currency.BRL, Money.of("10.00"));
        assertThat(daily.getDate()).isEqualTo(date.toLocalDate());
    }

    @Test
    void consolidated_amount_sums_transactions() {
        var amount = ConsolidatedAmount.ofTransactions(List.of(store("100.00"), store("-30.00")));
        assertThat(amount.getValue().getValue()).isEqualByComparingTo("70.00");
    }
}
