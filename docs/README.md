# Documentação de Arquitetura — Cash Flow Control

Esta pasta reúne as decisões e representações arquiteturais da solução, conforme
os requisitos obrigatórios e diferenciais do desafio de Arquiteto de Soluções.

## Índice

| Área | Documento |
|------|-----------|
| Domínios e capacidades de negócio | [domain/domain-map.md](domain/domain-map.md) |
| Requisitos funcionais e não-funcionais | [requirements/requirements.md](requirements/requirements.md) |
| Arquitetura Alvo (C4 + justificativas) | [architecture/target-architecture.md](architecture/target-architecture.md) |
| Diagramas PlantUML (C4) | [diagrams/](diagrams/README.md) |
| Arquitetura de Transição (legado → alvo) | [architecture/transition-architecture.md](architecture/transition-architecture.md) |
| Escalabilidade e capacidade | [nfr/scalability.md](nfr/scalability.md) |
| Resiliência | [nfr/resilience.md](nfr/resilience.md) |
| Observabilidade e monitoramento | [nfr/observability.md](nfr/observability.md) |
| Segurança para consumo de serviços | [nfr/security.md](nfr/security.md) |
| Estimativa de custos | [cost/cost-estimate.md](cost/cost-estimate.md) |
| Decisões arquiteturais (ADRs) | [adr/](adr/) |

## Resumo executivo

Um comerciante precisa registrar lançamentos (créditos/débitos) e consultar o
saldo diário consolidado. O requisito não-funcional central é: **o serviço de
lançamentos não pode ficar indisponível se o consolidado cair**, e em picos o
consolidado recebe **50 req/s com no máximo 5% de perda**.

A solução separa duas capacidades em **dois deployables independentes**:

- **transaction-service** (lançamentos) — escrita rápida e sempre disponível.
- **consolidation-service** (consolidado) — consome de forma assíncrona e mantém
  saldo e relatório diário.

O acoplamento é quebrado por um **outbox transacional + fila RabbitMQ**, com
**event-carried state transfer**: a mensagem carrega o estado completo do
lançamento, de forma que o consolidado nunca lê o banco do lançamento.
