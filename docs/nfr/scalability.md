# Escalabilidade e Capacidade

## Meta

Pico de **50 req/s HTTP** nas APIs do `consolidation-service` (`GET /balances`,
`GET /daily-transactions/{date}`) com **≤ 5% de perda** (RNF2/RNF3).

> A consolidação em si é assíncrona (RabbitMQ). O pico do requisito refere-se às
> requisições de **leitura** dos clientes, não ao throughput da fila.

## Por que a meta é confortável

1. **Leituras simples** — `GET /balances` e `GET /daily-transactions/{date}` são
   queries por índice no banco já consolidado; sem joins complexos ou locks.
2. **Virtual threads** — Tomcat usa virtual threads; threads nunca ficam bloqueadas
   aguardando I/O de banco, logo o serviço sustenta muito mais de 50 req/s por instância.
3. **Escala horizontal** — réplicas stateless atrás de load balancer; adicionar
   instâncias multiplica linearmente o throughput de leitura.

## Dimensionamento — APIs de leitura (RNF2/RNF3)

| Alavanca | Onde | Efeito |
|----------|------|--------|
| Virtual threads | `spring.threads.virtual.enabled=true` | Tomcat não bloqueia thread em I/O de banco; suporta alta concorrência por instância |
| Escala horizontal | réplicas stateless do `consolidation-service` | throughput de leitura escala linearmente |
| Connection pool | `HIKARI_MAX_POOL_SIZE` | Controla concorrência ao banco; ajustar conforme réplicas |
| Índices no banco | `daily_transaction_idx_date`, `balance` (PK) | Queries de leitura O(log n) |

## Dimensionamento — consumo assíncrono (fila)

| Alavanca | Onde | Efeito |
|----------|------|--------|
| Concorrência de consumidores | `RABBITMQ_CONCURRENCY` / `RABBITMQ_MAX_CONCURRENCY` | Mais threads processando a fila |
| Prefetch | `prefetch=20` | Lote de mensagens em voo por consumidor |
| Intervalo do dispatcher | `outbox.dispatch.fixed-delay` (padrão 500 ms) | Latência de publicação dos lançamentos; ShedLock garante que apenas uma instância executa por ciclo |

## Escala dos lançamentos

O `transaction-service` é stateless no caminho de escrita — escala
horizontalmente atrás de um load balancer; cada instância grava transação +
outbox atomicamente. O dispatcher do outbox usa **ShedLock** para coordenar
entre réplicas: somente uma instância publica por ciclo, evitando duplicatas.

## Modelo de threads

Ambos os serviços rodam com **virtual threads** (`spring.threads.virtual.enabled=true`).
Tomcat, `@Async` e os listeners do RabbitMQ usam virtual threads automaticamente,
eliminando o pool de threads de plataforma como gargalo sob alta concorrência de I/O.
