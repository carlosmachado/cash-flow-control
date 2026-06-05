# Cash Flow Control

Backend para controle de fluxo de caixa. A aplicação registra transações de crédito e
débito, mantém o saldo consolidado e gera uma visão diária das movimentações.

## Funcionalidades

- Registro de transações financeiras com data, tipo, valor e descrição.
- Consulta de todas as transações registradas.
- Consolidação assíncrona do saldo geral.
- Armazenamento de lançamentos por dia para relatório diário.
- Consulta do saldo atual.
- Consulta das transações de um dia com total consolidado.

## Stack

- Java 17, Spring Boot 2.4
- PostgreSQL + Flyway
- RabbitMQ
- Maven
- JPA/Hibernate, Lombok, ModelMapper
- Testcontainers para testes de integração

## Arquitetura

O projeto segue uma organização inspirada em Domain-Driven Design:

```text
domain/         agregados, value objects, eventos e serviços de domínio
application/    casos de uso da aplicação
infrastructure/ detalhes técnicos como AMQP, outbox, HTTP, Hibernate e timezone
presentation/   controllers REST
```

Quando uma transação é registrada, o domínio publica o evento
`TransactionRegistered`. Esse evento cria uma mensagem na tabela de outbox. Em perfil
`local`, um job periódico despacha as mensagens pendentes para duas filas RabbitMQ:

- `balance_update`: consolida a transação no saldo geral.
- `daily_balance_update`: salva a transação no relatório diário.

## Regras de negócio

- Transações do tipo `CREDIT` sempre entram com valor positivo.
- Transações do tipo `DEBIT` sempre entram com valor negativo.
- O saldo começa em `BRL 0.00` quando ainda não existe registro.
- Cada transação pode gerar apenas um lançamento diário.

## Endpoints

### `POST /transactions`

Registra uma nova transação.

```json
{
  "transactionDate": "2025-01-01T10:00:00",
  "type": "CREDIT",
  "amount": 100.00,
  "description": "Initial deposit"
}
```

### `GET /transactions`

Lista todas as transações registradas.

### `GET /balances`

Retorna o saldo consolidado atual.

### `GET /dailyTransactions/{date}`

Retorna as transações de uma data e o valor consolidado do dia.

```bash
curl "http://localhost:8080/dailyTransactions/2025-01-01"
```

## Como executar

Criar o container PostgreSQL:

```bash
docker run --name postgres-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d postgres
```

Criar a database da aplicação:

```bash
docker exec -it postgres-db psql -U postgres -c "create database cash_flow"
```

Criar a database dos testes integrados:

```bash
docker exec -it postgres-db psql -U postgres -c "create database cash_flow_test"
```

Criar o container RabbitMQ:

```bash
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management
```

Executar a aplicação:

```bash
./mvnw spring-boot:run
```

## Testes

```bash
./mvnw test
```
