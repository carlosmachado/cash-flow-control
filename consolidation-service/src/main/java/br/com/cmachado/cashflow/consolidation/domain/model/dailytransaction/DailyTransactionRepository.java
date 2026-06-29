package br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, DailyTransactionId> {

    Optional<List<DailyTransaction>> findAllByDate(LocalDate date);

    boolean existsByTransactionId(UUID transactionId);
}
