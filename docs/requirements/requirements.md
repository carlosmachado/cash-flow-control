# Refinamento de Requisitos

## Requisitos Funcionais

| ID | Requisito | Onde |
|----|-----------|------|
| RF1 | Registrar lançamento de crédito | `POST /transactions` (transaction-service) |
| RF2 | Registrar lançamento de débito | `POST /transactions` (transaction-service) |
| RF3 | Listar lançamentos registrados | `GET /transactions` (transaction-service) |
| RF4 | Consultar saldo consolidado atual | `GET /balances` (consolidation-service) |
| RF5 | Consultar lançamentos de um dia + total consolidado | `GET /daily-transactions/{date}` (consolidation-service) |
| RF6 | Consolidação assíncrona do saldo a cada lançamento | listener `balance_update` |
| RF7 | Registro diário idempotente por lançamento | listener `daily_balance_update` |

## Requisitos Não-Funcionais

| ID | Requisito | Meta | Estratégia |
|----|-----------|------|-----------|
| RNF1 | **Independência de disponibilidade** — lançamentos não podem cair se o consolidado cair | Lançamentos 100% disponíveis durante queda do consolidado | Deployables separados + outbox + fila; o `POST /transactions` só depende do banco do próprio serviço |
| RNF2 | **Vazão das APIs do consolidado em pico** | 50 req/s em `GET /balances` e `GET /daily-transactions/{date}` | Virtual threads + escala horizontal do consolidation-service; leituras são queries simples por índice |
| RNF3 | **Perda de requisições HTTP no pico** | ≤ 5% | Escala horizontal (réplicas stateless) + connection pool dimensionado; sem fila envolvida — é throughput de leitura HTTP |
| RNF4 | Consistência do saldo | Eventual, sem perda | Outbox transacional; consumidor idempotente |
| RNF5 | Observabilidade | Métricas + health | Actuator + Micrometer/Prometheus + Grafana |
| RNF6 | Segurança de integração | AuthN/Z nas APIs | OAuth2 Resource Server (JWT) acionável por perfil |
| RNF7 | Portabilidade de execução | Subir local em 1 comando | `docker-compose up` |

## Análise do requisito de pico (RNF2/RNF3)

> "Em dias de picos, o serviço de consolidado diário recebe 50 requisições por
> segundo, com no máximo 5% de perda."

O pico mencionado refere-se às **requisições HTTP** nas APIs de consulta do
`consolidation-service` (`GET /balances` e `GET /daily-transactions/{date}`),
não ao consumo de mensagens da fila (que é um fluxo interno assíncrono, separado).

- A consolidação do saldo ocorre **de forma assíncrona** via RabbitMQ — os
  listeners atualizam o banco; as APIs apenas lêem o resultado já consolidado.
- As APIs de leitura são **queries simples por índice** (sem joins complexos),
  suportando alta vazão com baixa latência.
- Com **virtual threads** e escala horizontal (réplicas stateless), 50 req/s é
  uma meta confortável: o gargalo é o banco, não o serviço, uma estratégia de cache pode ser utilizada no futuro.
- Perda ≤ 5% é garantida mantendo o pool de conexões e réplicas dimensionados
  para absorver o pico sem rejeitar requests.
- Detalhamento de capacidade em [../nfr/scalability.md](../nfr/scalability.md).
