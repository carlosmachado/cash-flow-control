# Escalabilidade e Capacidade

## Meta

Pico de **50 req/s** no consolidado com **≤ 5% de perda** (RNF2/RNF3).

## Por que a meta é confortável

1. **Desacoplamento por fila** — lançamentos publicam no RabbitMQ via outbox; o
   consolidado consome no próprio ritmo. A fila absorve rajadas (*load leveling*).
2. **Sem descarte** — outbox (entrega ao menos uma vez) + filas duráveis ⇒ nenhuma
   mensagem é perdida mesmo sob pico; perda efetiva ~0%, bem abaixo dos 5%.
3. **Consumo concorrente** — o consolidation-service usa múltiplos consumidores:
   `spring.rabbitmq.listener.simple.concurrency=4`,
   `max-concurrency=16`, `prefetch=20` (configurável por env).

## Dimensionamento

| Alavanca | Onde | Efeito |
|----------|------|--------|
| Concorrência de consumidores | `RABBITMQ_CONCURRENCY` / `RABBITMQ_MAX_CONCURRENCY` | Mais threads processando a fila |
| Escala horizontal | réplicas do `consolidation-service` | RabbitMQ distribui (round-robin) entre instâncias |
| Prefetch | `prefetch=20` | Lote de mensagens em voo por consumidor |
| Intervalo do dispatcher | `outbox.dispatch.fixed-delay` | Latência de publicação dos lançamentos |

A 50 msg/s, cada mensagem é um upsert simples; com 4–16 consumidores e prefetch
20, a vazão sustentada fica muito acima de 50/s. O gargalo prático é o banco, que
escala verticalmente e por índice (`daily_transaction_idx_date`).

## Escala dos lançamentos

O `transaction-service` é stateless no caminho de escrita — escala
horizontalmente atrás de um load balancer; cada instância grava transação +
outbox atomicamente.
