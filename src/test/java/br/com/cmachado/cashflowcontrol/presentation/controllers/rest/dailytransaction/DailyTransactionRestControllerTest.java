package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.ConsolidatedAmount;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransactionRepository;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.TestDailyTransaction;
import br.com.cmachado.cashflowcontrol.infrastructure.mapper.ModelMapperConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DailyTransactionRestController.class)
@Import(DailyTransactionRestController.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class DailyTransactionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyTransactionRepository dailyTransactionRepository;

    private final String route = "/dailyTransactions";

    @Test
    public void can_get_the_transactions_of_the_day() throws Exception {
        var now = LocalDate.now();
        var count = 2;

        var transactions = TestDailyTransaction.someTransactionsForDate(now, count);

        var consolidatedAmount = ConsolidatedAmount.ofTransactions(transactions);
        var consolidatedAmountString = consolidatedAmount.getValue().getValue().toString();

        doReturn(Optional.of(transactions))
                .when(dailyTransactionRepository)
                .findAllByDate(Mockito.any(LocalDate.class));

        mockMvc
                .perform(get(route + "/" + now)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(count)))
                .andExpect(jsonPath("$.consolidatedAmount", is(consolidatedAmountString)));

    }
}
