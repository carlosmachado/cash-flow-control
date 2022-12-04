package br.com.cmachado.cashflowcontrol.infrastructure.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutBoxRepository extends JpaRepository<OutBox, UUID>{
    Optional<List<OutBox>> findByDispatchedFalseOrderByCreatedAt();
}
