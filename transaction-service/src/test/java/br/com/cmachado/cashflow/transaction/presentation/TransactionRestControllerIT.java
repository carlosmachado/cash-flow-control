package br.com.cmachado.cashflow.transaction.presentation;

import br.com.cmachado.cashflow.transaction.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionRestControllerIT extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void post_credit_returns_201_with_positive_amount() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-01-01T10:00:00",
                                  "type": "CREDIT",
                                  "amount": 100.00,
                                  "description": "Deposito"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.type").value("CREDIT"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.currency").value("BRL"));
    }

    @Test
    void post_debit_returns_201_with_negative_amount() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-01-01T11:00:00",
                                  "type": "DEBIT",
                                  "amount": 50.00,
                                  "description": "Saque"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(-50.0));
    }

    @Test
    void post_missing_type_returns_400() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-01-01T10:00:00",
                                  "amount": 100.00,
                                  "description": "Missing type"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_missing_amount_returns_400() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-01-01T10:00:00",
                                  "type": "CREDIT",
                                  "description": "Missing amount"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_missing_description_returns_400() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-01-01T10:00:00",
                                  "type": "CREDIT",
                                  "amount": 100.00
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_all_returns_200_list() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-02-01T10:00:00",
                                  "type": "CREDIT",
                                  "amount": 200.00,
                                  "description": "List test"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void credit_with_negative_amount_is_flipped_to_positive() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionDate": "2025-03-01T10:00:00",
                                  "type": "CREDIT",
                                  "amount": -300.00,
                                  "description": "Negative credit should flip"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(300.0));
    }

    @Test
    void health_endpoint_is_up() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
