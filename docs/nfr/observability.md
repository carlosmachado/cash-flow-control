# Observabilidade e Monitoramento

## Stack

| Camada | Ferramenta |
|--------|-----------|
| Instrumentação | Spring Boot Actuator + Micrometer |
| Coleta de métricas | Prometheus (`/actuator/prometheus`) |
| Visualização | Grafana (datasource Prometheus provisionado) |
| Health | `/actuator/health` (readiness/liveness) |

Ambos os serviços expõem `health, info, metrics, prometheus` e marcam as métricas
com a tag `application` (nome do serviço).

## Topologia

Diagrama (PlantUML): [../diagrams/observability-topology.puml](../diagrams/observability-topology.puml)

Configuração de scrape em [../../observability/prometheus.yml](../../observability/prometheus.yml).

## O que monitorar (golden signals + negócio)

| Sinal | Métrica |
|-------|---------|
| Latência | `http_server_requests_seconds` |
| Tráfego | taxa de `POST /transactions` |
| Erros | `http_server_requests_seconds_count{status="5xx"}` |
| Saturação | JVM/CPU (`jvm_*`, `process_cpu_usage`) |
| **Backlog do outbox** | gauge custom de linhas `dispatched=false` (evolução) |
| **Lag de consumo** | profundidade das filas RabbitMQ (RabbitMQ exporter) |

## Acesso local

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (login anônimo, role Admin)
- Métricas brutas: http://localhost:8080/actuator/prometheus e :8081

## Evolução futura

- Tracing distribuído (OpenTelemetry) ligando `POST` → evento → consolidação.
- Alertas (Alertmanager) para backlog de outbox e lag de fila.
- RabbitMQ Prometheus exporter para profundidade de fila.
