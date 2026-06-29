# Cash Flow Control

Controle de fluxo de caixa de um comerciante: registro de lançamentos
(créditos/débitos) e saldo diário consolidado.

Monorepo Maven com **dois serviços independentes** + um shared kernel:

```
cash-flow-control/            parent pom (Spring Boot 3.3 / Java 21)
├── shared-kernel/            blocos DDD, Money, contrato de integração, JSON
├── transaction-service/      Lançamentos  (porta 8080) — produtor
├── consolidation-service/    Consolidado  (porta 8081) — consumidor
├── docs/                     documentação de arquitetura (C4, ADRs, NFR, custo)
├── observability/            Prometheus + provisioning do Grafana
└── docker-compose.yml        pg + rabbitmq + serviços + prometheus + grafana
```

A documentação completa de arquitetura está em **[docs/](docs/README.md)**.

## Arquitetura em uma frase

O `transaction-service` grava o lançamento e um registro **outbox** na mesma
transação; um dispatcher publica no **RabbitMQ**; o `consolidation-service`
consome e mantém saldo e relatório diário. A mensagem carrega o estado completo
do lançamento (*event-carried state*), então o consolidado **nunca** lê o banco
do lançamento — por isso lançamentos seguem disponíveis se o consolidado cair.

## Stack

Java 21, Spring Boot 3.3, PostgreSQL + Flyway, RabbitMQ, Spring AMQP,
JPA/Hibernate, ModelMapper, springdoc-openapi, Actuator + Micrometer/Prometheus,
Spring Security (OAuth2 Resource Server), Testcontainers, Docker Compose.

## Pré-requisito de build

O build exige **JDK 21** (o `java.version` é 21). Se o `java` padrão for outro:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)   # macOS
```

## Rodar tudo com Docker (recomendado)

```bash
docker compose up --build
```

Sobe PostgreSQL, RabbitMQ, os dois serviços, Prometheus e Grafana.

| Serviço | URL |
|---------|-----|
| transaction-service | http://localhost:8080 |
| consolidation-service | http://localhost:8081 |
| Swagger (cada serviço) | `/swagger-ui.html` |
| RabbitMQ management | http://localhost:15672 (guest/guest) |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |

## Rodar localmente (sem Docker)

Suba PostgreSQL e RabbitMQ e crie o banco:

```bash
docker run --name postgres-db -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=cash_flow -p 5432:5432 -d postgres:16
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management
```

Em dois terminais (cada serviço cria seu schema via Flyway no startup):

```bash
./mvnw -pl transaction-service -am spring-boot:run
./mvnw -pl consolidation-service -am spring-boot:run
```

## Usando a API

```bash
# 1) registra um crédito (transaction-service)
curl -X POST http://localhost:8080/transactions \
  -H 'Content-Type: application/json' \
  -d '{"transactionDate":"2025-01-01T10:00:00","type":"CREDIT","amount":100.00,"description":"Deposito"}'

# 2) lista lançamentos
curl http://localhost:8080/transactions

# 3) saldo consolidado (consolidation-service) — atualiza de forma assíncrona
curl http://localhost:8081/balances

# 4) lançamentos consolidados de um dia
curl http://localhost:8081/daily-transactions/2025-01-01
```

## Regras de negócio

- `CREDIT` entra com valor positivo; `DEBIT` com valor negativo (normalizado).
- Saldo inicia em `BRL 0.00`.
- Cada lançamento gera no máximo um registro diário (idempotência por `transaction_id`).

## Testes

```bash
./mvnw clean test
```

Testes unitários de domínio e aplicação nos três módulos (sem dependência de
infraestrutura). Evolução: testes de integração com Testcontainers (deps já
incluídas) cobrindo o fluxo outbox → fila → consolidação.

## Segurança

Por padrão as APIs ficam abertas (uso local). Para exigir JWT, defina
`app.security.jwt.enabled=true` e o `issuer-uri` — ver
[docs/nfr/security.md](docs/nfr/security.md).

## Observabilidade

`/actuator/health` e `/actuator/prometheus` em ambos os serviços; Prometheus e
Grafana já provisionados no compose — ver
[docs/nfr/observability.md](docs/nfr/observability.md).
