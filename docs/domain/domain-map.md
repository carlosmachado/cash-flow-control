# Domínios Funcionais e Capacidades de Negócio

## Domínio central

**Gestão de Fluxo de Caixa do Comerciante** — controlar entradas e saídas e
oferecer visão consolidada do saldo.

## Capacidades de negócio → Bounded Contexts


| Capacidade de negócio                                                       | Bounded Context | Deployable              | Tipo       |
| ---------------------------------------------------------------------------- | --------------- | ----------------------- | ---------- |
| Registrar lançamentos (crédito/débito)                                    | Lançamentos    | `transaction-service`   | Core       |
| Consolidar saldo e relatório diário                                        | Consolidação  | `consolidation-service` | Core       |
| Blocos de construção compartilhados (Money, DDD, contrato de integração) | Shared Kernel   | `shared-kernel` (lib)   | Supporting |

A separação segue **Domain-Driven Design**: cada contexto é um agregado coeso
com sua própria linguagem ubíqua e seu próprio schema de dados.

Diagrama (PlantUML): [../diagrams/domain-contexts.puml](../diagrams/domain-contexts.puml)

A relação entre os contextos é **Customer/Supplier**: Lançamentos é upstream
(produtor do evento), Consolidação é downstream (consumidor). O contrato é o
evento `TransactionRegisteredMessage`, versionável de forma independente.

## Linguagem Ubíqua


| Termo                                  | Definição                                                                               |
| -------------------------------------- | ----------------------------------------------------------------------------------------- |
| Lançamento (Transaction)              | Movimentação financeira: crédito (positivo) ou débito (negativo).                     |
| Crédito (Credit)                      | Lançamento que aumenta o saldo. Armazenado com valor positivo.                           |
| Débito (Debit)                        | Lançamento que reduz o saldo. Armazenado com valor negativo.                             |
| Saldo (Balance)                        | Soma acumulada de todos os lançamentos. Inicia em `BRL 0,00`.                           |
| Consolidado diário (DailyTransaction) | Lançamentos agrupados por dia, com total do dia.                                         |
| Money                                  | Value object monetário com escala 2 e moeda.                                             |
| Outbox                                 | Tabela que garante publicação confiável do evento na mesma transação do lançamento. |

## Invariantes de domínio

- Crédito sempre positivo; débito sempre negativo (normalizado no registro).
- Saldo começa em `BRL 0,00` quando não há registro.
- Cada lançamento gera no máximo um registro diário (idempotência por `transaction_id`).
