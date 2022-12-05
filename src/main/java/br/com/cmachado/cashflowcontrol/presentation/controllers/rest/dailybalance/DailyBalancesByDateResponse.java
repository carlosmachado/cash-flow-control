package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.dailybalance;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DailyBalancesByDateResponse {
    private List<DailyBalanceDTO> balances;
    private int count;
    private BigDecimal consolidatedAmount;

    public static DailyBalancesByDateResponse empty() {
        return DailyBalancesByDateResponse.builder()
                .balances(new ArrayList<>(0))
                .count(0)
                .build();
    }
}
