# Roadmap de Testes Automatizados — Barber-Spring

Objetivo: aprender testes automatizados do zero, entendendo o "porquê" de cada camada, usando este projeto como base prática. Organizado em blocos sequenciais — cada bloco depende do anterior.

## Bloco 0 — Setup

- [ ] Adicionar dependências no `pom.xml`: Mockito, Testcontainers (Postgres module), RestAssured, JaCoCo
- [ ] Criar estrutura de pastas em `src/test/java`:
  ```
  src/test/java/.../
  ├── unit/usecase/
  ├── integration/repository/
  ├── integration/security/
  └── e2e/
  ```
- [ ] Confirmar Docker rodando localmente (necessário pro Testcontainers)

## Bloco 1 — Teste Unitário

- [ ] Escrever `AppointmentUseCaseTest` (JUnit 5 + Mockito)
  - Regra: bloqueia agendamento duplicado por barbeiro + data
  - Mockar repository, isolar regra de negócio, sem Spring context
- [ ] Rodar teste isolado (`mvn test -Dtest=AppointmentUseCaseTest`)
- [ ] Entender: assert, mock, verify, given-when-then

## Bloco 2 — Teste de Integração

- [ ] Configurar Testcontainers com Postgres real (não H2)
- [ ] Escrever teste de repository (persistência JPA real)
- [ ] Escrever teste de segurança (fluxo JWT — geração/validação de token, RBAC por role)
- [ ] Entender: `@SpringBootTest`, contexto Spring real, diferença unit vs integration

## Bloco 3 — Teste E2E/API

- [ ] Escrever `AppointmentFlowTest` com RestAssured
  - Fluxo completo: login → token → criar agendamento → validar resposta HTTP
- [ ] Rodar app inteira localmente, testar como cliente HTTP externo
- [ ] Entender: diferença integration vs e2e, quando cada um vale o custo

## Bloco 4 — CI

- [ ] Criar workflow GitHub Actions (`.github/workflows/tests.yml`)
- [ ] Rodar os 3 níveis de teste a cada push/PR
- [ ] Configurar JaCoCo, gerar relatório de cobertura
- [ ] Entender: pipeline, gate de qualidade, feedback automático

## Ordem de execução

Unit → Integration → E2E → CI. Cada bloco só começa quando o anterior estiver rodando verde.
