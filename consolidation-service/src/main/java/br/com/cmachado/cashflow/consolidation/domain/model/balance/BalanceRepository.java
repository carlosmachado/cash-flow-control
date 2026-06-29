package br.com.cmachado.cashflow.consolidation.domain.model.balance;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, BalanceId> {

    /**
     * Locks the single balance row for update so concurrent consumers serialize
     * the running-total update at the database level (no lost updates).
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Balance b where b.id = :id")
    Optional<Balance> findByIdForUpdate(@Param("id") BalanceId id);
}
