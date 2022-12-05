package br.com.cmachado.cashflowcontrol.domain.model.dailybalance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyBalanceRepository extends JpaRepository<DailyBalance, DailyBalanceId> {
    Optional<List<DailyBalance>> findAllByDate(LocalDate date);
}
