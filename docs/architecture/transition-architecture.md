# Arquitetura de Transição (Legado → Alvo)

O ponto de partida foi um **monólito** Spring Boot 2.4 (um único deployable) que
já separava os contextos em pacotes e já usava outbox + RabbitMQ internamente.
A transição para os dois serviços foi feita de forma incremental e de baixo risco.

Diagrama (PlantUML): [../diagrams/transition-phases.puml](../diagrams/transition-phases.puml)

## Fases

1. **Monorepo multi-módulo** — converte o `pom.xml` em parent e extrai
   `shared-kernel` (blocos DDD, `Money`, contrato de integração). Sem mudança de
   comportamento.
2. **Dois deployables** — `transaction-service` (produtor) e
   `consolidation-service` (consumidor). Continuam usando o mesmo broker e a mesma
   instância de banco, com **schema por serviço**.
3. **Event-carried state transfer** — antes o consolidado relia a `Transaction`
   pelo `transactionId`; agora a mensagem carrega o estado completo
   (`TransactionRegisteredMessage`), eliminando o acoplamento ao banco do
   produtor. Esse é o passo que torna o RNF1 verdadeiro.
4. **Modernização** — upgrade para Spring Boot 3.3 / Java 21
   (`javax` → `jakarta`, springfox → springdoc), Actuator/Prometheus e segurança
   JWT acionável.

## Estado futuro (fora do escopo do desafio)

- **DB por serviço** (instâncias separadas) para isolamento físico total — ver
  [../adr/0004-shared-database-schema-per-service.md](../adr/0004-shared-database-schema-per-service.md).
- **CI/CD** + registry de imagens; **escala horizontal** do consolidado.
- **DLQ** dedicada e política de retry/backoff explícita.
- **CQRS**: read-model otimizado para o relatório diário.
- **Kafka** caso a vazão cresça muito além de 50 req/s.
