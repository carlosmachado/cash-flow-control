# ADR-0001: Separar lançamentos e consolidado em dois deployables

## Status
Aceito

## Contexto
O RNF central exige que o serviço de lançamentos continue disponível mesmo se o
consolidado cair. Na versão anterior, ambos rodavam no mesmo processo (um
deployable), então uma falha do consolidado podia derrubar os lançamentos.

## Decisão
Separar em dois deployables independentes — `transaction-service` e
`consolidation-service` — em um monorepo Maven multi-módulo, com um
`shared-kernel` para blocos comuns.

## Consequências
- (+) Isolamento de disponibilidade e de escala (RNF1, RNF2).
- (+) Deploy e evolução independentes por contexto.
- (−) Mais complexidade operacional (dois processos, mensageria).
- (−) Consistência passa a ser eventual entre lançamento e saldo.
