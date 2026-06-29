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
| RNF2 | **Vazão do consolidado em pico** | 50 req/s | Consumidores concorrentes (prefetch + concurrency) e escala horizontal do consolidation-service |
| RNF3 | **Perda de requisições no pico** | ≤ 5% | Outbox (entrega ao menos uma vez) + filas duráveis ⇒ perda efetiva ~0% |
| RNF4 | Consistência do saldo | Eventual, sem perda | Outbox transacional; consumidor idempotente |
| RNF5 | Observabilidade | Métricas + health | Actuator + Micrometer/Prometheus + Grafana |
| RNF6 | Segurança de integração | AuthN/Z nas APIs | OAuth2 Resource Server (JWT) acionável por perfil |
| RNF7 | Portabilidade de execução | Subir local em 1 comando | `docker-compose up` |

## Análise do requisito de pico (RNF2/RNF3)

> "Em dias de picos, o serviço de consolidado diário recebe 50 requisições por
> segundo, com no máximo 5% de perda."

- O pico **não chega via HTTP** ao consolidado; chega como **mensagens** geradas
  pelos lançamentos. O consolidado consome no seu próprio ritmo a partir da fila.
- A fila atua como **buffer (load leveling)**: rajadas de 50 msg/s são absorvidas
  mesmo que o consolidado processe temporariamente mais devagar.
- Como o outbox garante entrega ao menos uma vez e as filas são duráveis, não há
  descarte de mensagens — a perda fica **muito abaixo dos 5%** tolerados.
- Detalhamento de capacidade em [../nfr/scalability.md](../nfr/scalability.md).
