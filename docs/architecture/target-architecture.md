# Arquitetura Alvo

## Visão geral

Dois serviços independentes, comunicação assíncrona por eventos, padrão
**Transactional Outbox** + **event-carried state transfer**.

> Diagramas em PlantUML / C4. Fontes `.puml` em [../diagrams/](../diagrams/README.md).

## C4 — Nível 1: Contexto

Diagrama (PlantUML): [../diagrams/c4-context.puml](../diagrams/c4-context.puml)

## C4 — Nível 2: Contêineres

Diagrama (PlantUML): [../diagrams/c4-container.puml](../diagrams/c4-container.puml)

> Banco compartilhado (uma instância PostgreSQL) com **schema por serviço**
> (`transaction` e `consolidation`). Cada serviço só acessa o próprio schema.
> Ver trade-off em [../adr/0004-shared-database-schema-per-service.md](../adr/0004-shared-database-schema-per-service.md).

## C4 — Nível 3: Componentes (transaction-service)

Diagrama (PlantUML): [../diagrams/c4-component-transaction-service.puml](../diagrams/c4-component-transaction-service.puml)

## C4 — Nível 3: Componentes (consolidation-service)

Diagrama (PlantUML): [../diagrams/c4-component-consolidation-service.puml](../diagrams/c4-component-consolidation-service.puml)

## Fluxo de um lançamento (sequência)

Diagrama (PlantUML): [../diagrams/sequence-transaction-flow.puml](../diagrams/sequence-transaction-flow.puml)

O `POST /transactions` confirma para o cliente **antes** de qualquer interação
com RabbitMQ ou com o consolidado.

## Justificativa das escolhas


| Decisão                                     | Alternativas                    | Por quê                                                                            |
| -------------------------------------------- | ------------------------------- | ----------------------------------------------------------------------------------- |
| **Microsserviços (2 deployables)**          | Monólito modular               | RNF1 exige isolamento de disponibilidade entre lançamento e consolidado. ADR-0001. |
| **Mensageria assíncrona (RabbitMQ)**        | Chamada síncrona REST          | Desacopla disponibilidade e serviços. ADR-0002.                                    |
| **Transactional Outbox**                     | Publicar direto no broker       | Evita perda de evento se o broker estiver fora. ADR-0003.                           |
| **Event-carried state transfer**             | Consumidor consultar o produtor | Mantém o consolidado independente do banco/serviço de lançamentos. ADR-0005.     |
| **Spring Boot 3 / Java 21**                  | Manter Boot 2.4 / Java 17       | Suporte LTS, virtual threads, Micrometer/observabilidade nativos.                   |
| **PostgreSQL**                               | NoSQL                           | Dados financeiros relacionais, consistência forte por serviço.                    |
| **Banco compartilhado, schema por serviço** | DB por serviço                 | Simplicidade; produção recomenda DB por serviço. ADR-0004.                      |

## Mapeamento para código

- `transaction-service/` — produtor (outbox + dispatcher).
- `consolidation-service/` — consumidor (listeners + saldo/relatório).
- `shared-kernel/` — `Money`, blocos DDD, contrato `TransactionRegisteredMessage`,
  `JsonSupport` (serialização compartilhada).
