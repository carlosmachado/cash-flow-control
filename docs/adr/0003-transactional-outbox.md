# ADR-0003: Transactional Outbox para publicação confiável

## Status

Aceito

## Contexto

Publicar direto no broker dentro do fluxo do `POST /transactions` cria o problema
do *dual write*: se o broker falhar após o commit do banco (ou vice-versa), o
evento se perde.

## Decisão

Gravar o evento numa tabela `outbox` **na mesma transação** do lançamento. Um
dispatcher agendado lê os pendentes e publica no RabbitMQ, marcando como
`dispatched`.

## Consequências

- (+) Entrega ao menos uma vez; perda efetiva ~0% (RNF3).
- (+) `POST /transactions` não depende do broker → reforça o RNF1.
- (−) Latência de publicação = intervalo do dispatcher (`outbox.dispatch.fixed-delay`, padrão 500 ms).
- (−) Consumidores precisam ser idempotentes (ver ADR-0005).
- (+) Dispatcher protegido por **ShedLock** (`transaction.shedlock`): em múltiplas instâncias apenas uma executa por ciclo, evitando duplicatas na publicação.
