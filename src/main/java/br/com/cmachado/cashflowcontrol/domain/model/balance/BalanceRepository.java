package br.com.cmachado.cashflowcontrol.domain.model.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, BalanceId> {


}
