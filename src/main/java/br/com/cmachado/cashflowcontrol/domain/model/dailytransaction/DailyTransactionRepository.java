package br.com.cmachado.cashflowcontrol.domain.model.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, DailyTransactionId> {

    Optional<List<DailyTransaction>> findAllByDate(LocalDate date);

    boolean existsByTransactionId(TransactionId transactionId);
}
