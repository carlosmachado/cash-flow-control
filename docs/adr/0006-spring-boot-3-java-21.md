# ADR-0006: Upgrade para Spring Boot 3 / Java 21

## Status
Aceito

## Contexto
A versão anterior usava Spring Boot 2.4 / Java 17 com bibliotecas datadas
(springfox, `javax.*`). Para uma entrega em 2026 e suporte de longo prazo,
modernizar é desejável.

## Decisão
Atualizar para **Spring Boot 3.3 / Java 21 (LTS)**:
- `javax.*` → `jakarta.*` (persistence, validation).
- springfox → **springdoc-openapi** (springfox não suporta Boot 3).
- Actuator + Micrometer/Prometheus nativos para observabilidade.
- Geração de DDL via Hibernate substituída por **migrations Flyway explícitas**
  por serviço (mais simples e portável).

## Consequências
- (+) Stack atual, suportada, com observabilidade e segurança de primeira classe.
- (+) Performance e recursos de linguagem do Java 21.
- (−) Esforço de migração de namespace e troca de libs.
- Nota: o build exige **JDK 21** (o ambiente tem JDK 26 como padrão).
