package br.com.cmachado.cashflow.consolidation.domain.service;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceId;
import br.com.cmachado.cashflow.consolidation.domain.model.balance.BalanceRepository;
import br.com.cmachado.cashflow.shared.contract.TransactionRegisteredMessage;
import br.com.cmachado.cashflow.shared.money.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceConsolidatorServiceTest {

    @Mock
    BalanceRepository balanceRepository;

    private TransactionRegisteredMessage message(String amount) {
        return new TransactionRegisteredMessage(
                UUID.randomUUID().toString(), "CREDIT", new BigDecimal(amount), "BRL", LocalDateTime.now());
    }

    @Test
    void applies_signed_amount_to_a_fresh_balance() {
        when(balanceRepository.findByIdForUpdate(any(BalanceId.class))).thenReturn(Optional.empty());

        var service = new BalanceConsolidatorService(balanceRepository);
        service.consolidate(message("100.00"));

        var captor = ArgumentCaptor.forClass(Balance.class);
        verify(balanceRepository).save(captor.capture());
        assertThat(captor.getValue().getAmount().getValue()).isEqualByComparingTo("100.00");
    }

    @Test
    void debit_reduces_existing_balance() {
        var existing = Balance.start();
        existing.apply(Money.of("100.00"));
        when(balanceRepository.findByIdForUpdate(any(BalanceId.class))).thenReturn(Optional.of(existing));

        var service = new BalanceConsolidatorService(balanceRepository);
        service.consolidate(message("-30.00"));

        assertThat(existing.getAmount().getValue()).isEqualByComparingTo("70.00");
    }
}
