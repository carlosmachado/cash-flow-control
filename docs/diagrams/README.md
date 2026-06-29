# Diagramas (PlantUML)

Todos os diagramas da documentação são **PlantUML** (código `.puml`). Visualize
direto na IDE — VS Code (extensão *PlantUML*) ou IntelliJ (plugin *PlantUML
Integration*) renderizam o preview. Os documentos de arquitetura linkam cada
`.puml`; nenhuma imagem é versionada.

| Arquivo | Usado em |
|---------|----------|
| [c4-context.puml](c4-context.puml) | C4 Nível 1 — Contexto ([target-architecture](../architecture/target-architecture.md)) |
| [c4-container.puml](c4-container.puml) | C4 Nível 2 — Contêineres |
| [c4-component-transaction-service.puml](c4-component-transaction-service.puml) | C4 Nível 3 — Componentes |
| [sequence-transaction-flow.puml](sequence-transaction-flow.puml) | Sequência — fluxo do lançamento |
| [domain-contexts.puml](domain-contexts.puml) | Bounded contexts ([domain-map](../domain/domain-map.md)) |
| [transition-phases.puml](transition-phases.puml) | [Arquitetura de transição](../architecture/transition-architecture.md) |
| [resilience-flow.puml](resilience-flow.puml) | [Resiliência](../nfr/resilience.md) |
| [observability-topology.puml](observability-topology.puml) | [Observabilidade](../nfr/observability.md) |
| [security-defense.puml](security-defense.puml) | [Segurança](../nfr/security.md) |

## Exportar imagem (opcional)

```bash
# via Docker (sem instalar nada) — a partir da raiz do repositório
docker run --rm -v "$PWD:/work" -w /work plantuml/plantuml -tpng docs/diagrams/*.puml
# ou: plantuml -tpng docs/diagrams/*.puml
```

Os diagramas C4 usam `!include` da biblioteca C4-PlantUML (precisa de rede na
primeira renderização/preview).
