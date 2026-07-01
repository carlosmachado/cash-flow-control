# ADR-0005: Event-carried state transfer no contrato

## Status
Aceito

## Contexto
Na versão anterior, o consolidado recebia apenas o `transactionId` e **relia** a
`Transaction` no banco. Com a separação em dois serviços, isso obrigaria o
consolidado a acessar o banco/serviço de lançamentos — recriando o acoplamento
que o RNF1 quer evitar.

## Decisão
A mensagem `TransactionRegisteredMessage` carrega o **estado completo** do
lançamento. O consolidado processa
apenas a mensagem, sem nunca ler o schema de lançamentos.

## Consequências
- (+) Consolidado totalmente independente do produtor.
- (+) Consumidor idempotente por `transaction_id`.
- (−) Contrato de evento precisa ser versionado com cuidado.
- (−) Pequena duplicação de dados entre serviços.
