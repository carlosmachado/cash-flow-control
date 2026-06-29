# Estimativa de Custos (Infraestrutura e Licenças)

## Licenças

**Custo de licença: R$ 0.** Toda a stack é open source:

| Componente | Licença |
|-----------|---------|
| Java 21 (Temurin), Spring Boot 3 | GPL+CE / Apache 2.0 |
| PostgreSQL | PostgreSQL License |
| RabbitMQ | MPL 2.0 |
| Prometheus / Grafana | Apache 2.0 / AGPL |

## Infraestrutura — cenário gerenciado (ordem de grandeza)

Estimativa mensal aproximada para um ambiente produtivo pequeno em nuvem
(valores ilustrativos em USD; variam por provedor/região):

| Item | Dimensionamento | Custo/mês (aprox.) |
|------|-----------------|--------------------|
| transaction-service | 2 réplicas, container pequeno (0.5 vCPU/1GB) | ~US$ 30 |
| consolidation-service | 2 réplicas, container pequeno | ~US$ 30 |
| PostgreSQL gerenciado | instância pequena + storage/backup | ~US$ 60 |
| RabbitMQ gerenciado | tier pequeno | ~US$ 50 |
| Observabilidade | Prometheus+Grafana self-hosted (1 container) ou tier free | ~US$ 20 |
| Rede/Load Balancer | 1 LB + egress | ~US$ 25 |
| **Total** | | **~US$ 215/mês** |

## Alavancas de custo

- **Escalar só o consolidado** sob pico (o gargalo é o consumo), mantendo
  lançamentos enxutos.
- **Free tiers** de observabilidade gerenciada reduzem o item de monitoramento.
- **Banco compartilhado** (schema por serviço) reduz custo agora; migrar para DB
  por serviço aumenta isolamento e custo — decisão de produção.
- Autoscaling por profundidade de fila evita superprovisionamento fora de pico.

## Premissas

- Tráfego compatível com o pico declarado (50 req/s no consolidado).
- Sem custo de licença comercial.
- Valores são ordem de grandeza para justificar trade-offs, não cotação.
