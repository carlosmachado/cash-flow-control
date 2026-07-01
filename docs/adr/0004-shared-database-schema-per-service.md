# ADR-0004: Banco compartilhado com schema por serviço

## Status
Aceito (com ressalva)

## Contexto
Microsserviços idealmente têm banco por serviço. Para o desafio, priorizou-se
simplicidade operacional de execução local.

## Decisão
Uma instância PostgreSQL (`cash_flow`) com **schema por serviço**:
`transaction` (tabelas `transaction`, `outbox`) e `consolidation` (tabelas
`balance`, `daily_transaction`). Cada serviço acessa **somente** seu schema; cada
um tem suas próprias migrations Flyway.

## Consequências
- (+) Menos infraestrutura para subir e operar localmente.
- (+) Fronteira de dados ainda respeitada no nível de schema/código.
- (−) Isolamento físico parcial: a instância é um ponto comum.
- Ressalva: em produção, talvez valha usar **instâncias separadas por serviço** para
  isolamento total de disponibilidade e escala. Ver
  [transition-architecture](../architecture/transition-architecture.md).
