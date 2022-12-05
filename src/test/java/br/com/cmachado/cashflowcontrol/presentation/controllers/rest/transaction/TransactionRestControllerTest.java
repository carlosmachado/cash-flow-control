package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction;

import br.com.cmachado.cashflowcontrol.application.transaction.TransactionService;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TestTransaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import br.com.cmachado.cashflowcontrol.infrastructure.mapper.ModelMapperConfig;
import br.com.cmachado.cashflowcontrol.utils.Jsons;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionRestController.class)
@Import(TransactionRestController.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class TransactionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionServiceImpl;

    @MockBean
    private TransactionRepository transactionRepository;

    private final String route = "/transactions";

    @Test
    public void can_register_transaction() throws Exception {
        var type = Transaction.Type.CREDIT;
        var amount = "100.00";
        var transaction = TestTransaction.aTransaction(type, amount);

        doReturn(transaction)
                .when(transactionServiceImpl)
                .execute(Mockito.any(RegisterTransactionRequest.class));

        var request = new RegisterTransactionRequest();
        request.setTransactionDate(LocalDateTime.now());
        request.setDescription("transaction test");
        request.setType(type);
        request.setAmount(new BigDecimal(amount));

        var json = Jsons.toJson(request);

        mockMvc
                .perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(transaction.getId().getValueString())))
                .andExpect(jsonPath("$.type", is(type.name())));

    }
}
