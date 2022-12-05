package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.utils.PostgreSQLExtension;
import br.com.cmachado.cashflowcontrol.utils.TableNamesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("itst")
@ExtendWith(PostgreSQLExtension.class)
class TransactionRepositoryIT {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TableNamesUtil.from(Transaction.class));
    }

    @Test
    void can_save_a_transaction() {

        var found = JdbcTestUtils.countRowsInTable(jdbcTemplate, TableNamesUtil.from(Transaction.class));

        assertEquals(0, found);

        var transaction = TestTransaction.aTransaction(Transaction.Type.DEBIT, "-100.00");

        transactionRepository.save(transaction);

        found = JdbcTestUtils.countRowsInTable(jdbcTemplate, TableNamesUtil.from(Transaction.class));

        assertEquals(1, found);
    }
}
