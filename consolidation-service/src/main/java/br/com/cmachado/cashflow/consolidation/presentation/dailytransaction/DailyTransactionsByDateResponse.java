package br.com.cmachado.cashflow.consolidation.presentation.dailytransaction;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DailyTransactionsByDateResponse {
    private List<DailyTransactionDTO> balances;
    private int count;
    private String consolidatedAmount;

    public static DailyTransactionsByDateResponse empty() {
        return DailyTransactionsByDateResponse.builder()
                .balances(new ArrayList<>(0))
                .count(0)
                .build();
    }
}
