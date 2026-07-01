# Documentação de Arquitetura — Cash Flow Control

Esta pasta reúne as decisões e representações arquiteturais da solução.

## Índice


| Área                                       | Documento                                                                          |
| ------------------------------------------- | ---------------------------------------------------------------------------------- |
| Domínios e capacidades de negócio         | [domain/domain-map.md](domain/domain-map.md)                                       |
| Requisitos funcionais e não-funcionais     | [requirements/requirements.md](requirements/requirements.md)                       |
| Arquitetura Alvo (C4 + justificativas)      | [architecture/target-architecture.md](architecture/target-architecture.md)         |
| Diagramas PlantUML (C4)                     | [diagrams/](diagrams/README.md)                                                    |
| Arquitetura de Transição (legado → alvo) | [architecture/transition-architecture.md](architecture/transition-architecture.md) |
| Escalabilidade e capacidade                 | [nfr/scalability.md](nfr/scalability.md)                                           |
| Resiliência                                | [nfr/resilience.md](nfr/resilience.md)                                             |
| Observabilidade e monitoramento             | [nfr/observability.md](nfr/observability.md)                                       |
| Segurança para consumo de serviços        | [nfr/security.md](nfr/security.md)                                                 |
| Estimativa de custos                        | [cost/cost-estimate.md](cost/cost-estimate.md)                                     |
| Decisões arquiteturais (ADRs)              | [adr/](adr/)                                                                       |

## Resumo

Um comerciante precisa registrar lançamentos (créditos/débitos) e consultar o
saldo diário consolidado.

A solução separa duas capacidades em **dois deployables independentes**:

- **transaction-service** (lançamentos) — escrita rápida e sempre disponível.
- **consolidation-service** (consolidado) — consome de forma assíncrona e mantém
  saldo e relatório diário.
