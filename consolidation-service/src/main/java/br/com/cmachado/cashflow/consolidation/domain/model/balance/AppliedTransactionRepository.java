package br.com.cmachado.cashflow.consolidation.domain.model.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppliedTransactionRepository extends JpaRepository<AppliedTransaction, UUID> {
}
