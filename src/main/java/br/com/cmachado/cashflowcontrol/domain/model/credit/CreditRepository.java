package br.com.cmachado.cashflowcontrol.domain.model.credit;

import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, TransactionId> {


}
