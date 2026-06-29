# Resiliência

## Requisito central (RNF1)

O `transaction-service` **não pode** ficar indisponível quando o
`consolidation-service` cair.

## Como é garantido

Diagrama (PlantUML): [../diagrams/resilience-flow.puml](../diagrams/resilience-flow.puml)

- O caminho de escrita do lançamento termina no **commit do outbox** — não toca
  RabbitMQ nem o consolidado. Se o consolidado (linha tracejada) estiver fora, o
  `POST /transactions` continua retornando `201`.
- Mensagens se acumulam no outbox/fila e são drenadas quando o consolidado volta
  (recuperação automática, sem perda).

## Mecanismos

| Risco | Mitigação |
|-------|-----------|
| Consolidado fora do ar | Outbox acumula; fila durável retém; drena ao voltar |
| Broker fora do ar | Outbox retém no banco; dispatcher tenta de novo no próximo ciclo |
| Mensagem reprocessada | Consumidor idempotente (`existsByTransactionId`) |
| Falha ao publicar um item | Dispatcher trata por item e segue para o próximo (`retry`) |
| Reinício do broker | Filas `durable=true` |

## Teste de resiliência (manual)

1. `docker-compose up`
2. Pare o consolidado: `docker compose stop consolidation-service`
3. `POST /transactions` → ainda retorna `201` (prova do RNF1)
4. Suba de novo: `docker compose start consolidation-service`
5. `GET /balances` reflete os lançamentos acumulados (backlog drenado)

## Evolução futura

- Dead-letter queue + retry com backoff exponencial.
- Health checks de readiness para o broker.
