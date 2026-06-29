# ADR-0002: Comunicação assíncrona via RabbitMQ

## Status
Aceito

## Contexto
Os dois serviços precisam se comunicar sem acoplar disponibilidade, e o
consolidado recebe picos de 50 req/s.

## Decisão
Usar mensageria assíncrona (RabbitMQ) com filas `balance_update` e
`daily_balance_update`. O lançamento publica eventos; o consolidado consome.

## Alternativas
- Chamada síncrona REST do lançamento para o consolidado — rejeitada: acopla
  disponibilidade (fere o RNF1) e não absorve picos.
- Kafka — adiado: maior custo operacional; RabbitMQ atende a vazão atual.

## Consequências
- (+) Desacoplamento de disponibilidade; *load leveling* do pico via fila.
- (+) Consumo concorrente e escalável.
- (−) Necessita broker e tratamento de idempotência/reprocesso.
