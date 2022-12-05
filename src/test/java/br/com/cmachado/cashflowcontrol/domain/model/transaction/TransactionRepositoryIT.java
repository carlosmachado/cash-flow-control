package br.com.cmachado.cashflowcontrol.domain.model.transaction;

import br.com.cmachado.cashflowcontrol.utils.TableNamesUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionRepositoryIT {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, getTableName());
    }

    @Test
    public void can_save_a_transaction() {

        var found = JdbcTestUtils.countRowsInTable(jdbcTemplate, getTableName());

        assertEquals(0, found);

        var transaction = TestTransaction.aTransaction(Transaction.Type.DEBIT, "-100.00");

        transactionRepository.save(transaction);

        found = JdbcTestUtils.countRowsInTable(jdbcTemplate, getTableName());

        assertEquals(1, found);
    }

    @NotNull
    private static String getTableName() {
        return TableNamesUtil.from(Transaction.class);
    }
}
