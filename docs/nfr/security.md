# Segurança para Consumo (Integração) de Serviços

## Modelo

As APIs REST podem operar como **OAuth2 Resource Server (JWT Bearer)**. O
comportamento é acionável por configuração, para não atrapalhar o uso local:

| Perfil | `app.security.jwt.enabled` | Comportamento |
|--------|---------------------------|---------------|
| Local/desafio (padrão) | `false` | Endpoints abertos (curl/compose sem fricção) |
| Seguro | `true` | Exige JWT válido; valida via `issuer-uri` |

Implementado em `SecurityConfig` de cada serviço. Mesmo no modo seguro,
`/actuator/**` e a documentação OpenAPI seguem acessíveis (scraping/docs).

### Ativando o modo seguro

```properties
app.security.jwt.enabled=true
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://<seu-idp>/
```

Requisições passam a exigir `Authorization: Bearer <token>`.

## Critérios de segurança de integração

| Critério | Abordagem |
|----------|-----------|
| Autenticação serviço-a-serviço | JWT emitido por IdP (client credentials) |
| Autorização | Escopos/roles no token (ex.: `transactions:write`, `balance:read`) |
| Transporte | TLS ponta a ponta; **mTLS** entre serviços em produção (service mesh) |
| Segredos | Variáveis de ambiente / secret manager — nada hardcoded |
| Superfície de rede | Broker e banco em rede privada; somente APIs expostas via gateway |
| Mensageria | Credenciais do RabbitMQ por ambiente; vhost dedicado |

## Defesa em profundidade (produção)

Diagrama (PlantUML): [../diagrams/security-defense.puml](../diagrams/security-defense.puml)

## Evolução futura

- Rotação automática de credenciais; rate limiting no gateway.
- Auditoria de acesso e mascaramento de dados sensíveis em logs.
