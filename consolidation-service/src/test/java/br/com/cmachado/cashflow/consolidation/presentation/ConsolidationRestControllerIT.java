package br.com.cmachado.cashflow.consolidation.presentation;

import br.com.cmachado.cashflow.consolidation.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConsolidationRestControllerIT extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    // --- /balances ---

    @Test
    void get_balance_returns_200_with_zero_when_empty() throws Exception {
        mockMvc.perform(get("/balances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("BRL"))
                .andExpect(jsonPath("$.amount").value(0.0));
    }

    // --- /daily-transactions ---

    @Test
    void get_by_date_returns_empty_when_no_transactions() throws Exception {
        mockMvc.perform(get("/daily-transactions/2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.balances").isEmpty());
    }

    @Test
    void get_by_invalid_date_returns_500() throws Exception {
        // DateTimeParseException not specifically mapped in CustomGlobalExceptionHandler → 500
        mockMvc.perform(get("/daily-transactions/not-a-date"))
                .andExpect(status().isInternalServerError());
    }

    // --- actuator ---

    @Test
    void health_endpoint_is_up() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
