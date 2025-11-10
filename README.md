# Reagentes API

API REST para gestão de reagentes de laboratório, fabricantes, localizações de estoque e movimentações. Projeto desenvolvido com Spring Boot 3.5.7 (Java 21) e banco H2 em memória.

## 👥 Equipe (Nomes e RMs)

| Integrante | RM |
| --- | --- |
| Alice Santos Bulhões | RM554499 |


## ✅ Requisitos

- JDK 21 instalado (necessário para Spring Boot 3.5.x e para compilar o projeto)
- Git (opcional)
- Não é necessário instalar o Maven: o projeto usa o Maven Wrapper (`mvnw`/`mvnw.cmd`).

## ▶️ Como executar

Na raiz do projeto (`reagentes_api`), execute um dos fluxos abaixo.

### 1) Executar direto (modo desenvolvimento)

```bash
./mvnw spring-boot:run
```

- A aplicação sobe em: http://localhost:8080
- Logs no console indicam quando a aplicação estiver pronta.

### 2) Empacotar e rodar o JAR

```bash
./mvnw -DskipTests package
java -jar target/reagentes_api-0.0.1-SNAPSHOT.jar
```

### 3) Rodar testes

```bash
./mvnw test
```

## ⚙️ Configuração

O arquivo `src/main/resources/application.properties` contém as propriedades básicas do projeto. Por padrão:

- `server.port` não foi definido, então a API usa `8080`.
- Dependência `H2` em memória: os dados são voláteis e se perdem a cada reinicialização.

Se desejar habilitar o console do H2, adicione (opcional):

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 🔗 Convenções da API

- Base URL: `http://localhost:8080/api`
- Formato: JSON
- Datas:
  - `LocalDate`: `YYYY-MM-DD` (ex.: `2025-11-09`)
  - `LocalDateTime`: `YYYY-MM-DDTHH:mm:ss` (ex.: `2025-11-09T14:30:00`)
- Enums: use as constantes exatamente como declaradas no código (maiúsculas):
  - `StatusReagente`: `QUARENTENA`, `LIBERADO`, `EM_USO`, `VENCIDO`, `REPROVADO_CONTROLE_QUALIDADE`, `DESCARTADO`
  - `TipoLocalizacaoEstoque`: `REFRIGERADOR`, `FREEZER_MINUS20`, `FREEZER_MINUS80`, `PRATELEIRA_AMBIENTE`, `OUTRO`
  - `TipoMovimentacao`: `ENTRADA_NOTA`, `SAIDA_USO_ANALISADOR`, `SAIDA_DESCARTE_VENCIMENTO`, `SAIDA_DESCARTE_CONTROLE_QUALIDADE`, `AJUSTE_INVENTARIO_POSITIVO`, `AJUSTE_INVENTARIO_NEGATIVO`

## 📚 Endpoints

Abaixo, o resumo dos principais recursos e exemplos de uso via cURL.

### Fabricantes (`/api/fabricantes`)

- GET `/api/fabricantes` → 200 OK: lista de fabricantes
- GET `/api/fabricantes/{id}` → 200 OK | 404 Not Found
- POST `/api/fabricantes` → 201 Created
- PUT `/api/fabricantes/{id}` → 200 OK | 404 Not Found
- DELETE `/api/fabricantes/{id}` → 204 No Content | 404 Not Found

Exemplo de criação:

```bash
curl -X POST http://localhost:8080/api/fabricantes \
  -H "Content-Type: application/json" \
  -d '{
    "nomeOficial": "ACME Chemicals Ltd.",
    "nomeFantasia": "ACME",
    "cnpj": "12.345.678/0001-99",
    "paisOrigem": "Brasil"
  }'
```

### Localizações (`/api/localizacoes`)

- GET `/api/localizacoes` → 200 OK
- GET `/api/localizacoes/{id}` → 200 OK | 404 Not Found
- POST `/api/localizacoes` → 201 Created
- PUT `/api/localizacoes/{id}` → 200 OK | 404 Not Found
- DELETE `/api/localizacoes/{id}` → 204 No Content | 404 Not Found

Exemplo de criação:

```bash
curl -X POST http://localhost:8080/api/localizacoes \
  -H "Content-Type: application/json" \
  -d '{
    "codigoLocal": "FRZ-01",
    "descricao": "Freezer -20C da sala 3",
    "tipo": "FREEZER_MINUS20",
    "faixaTemperaturaNominal": "-20 ± 5C",
    "setor": "Química"
  }'
```

### Reagentes (`/api/reagentes`)

- GET `/api/reagentes` → 200 OK
- GET `/api/reagentes/{id}` → 200 OK | 404 Not Found
- POST `/api/reagentes` → 201 Created
- PUT `/api/reagentes/{id}` → 200 OK | 404 Not Found
- DELETE `/api/reagentes/{id}` → 204 No Content | 404 Not Found

Estrutura do `ReagenteDTO`:

```json
{
  "id": "UUID opcional (gerado pelo sistema)",
  "nome": "string",
  "codigoSku": "string",
  "lote": "string",
  "dataValidade": "YYYY-MM-DD",
  "dataRecebimento": "YYYY-MM-DD",
  "quantidadeEmEstoque": 0,
  "status": "LIBERADO|EM_USO|...",
  "fabricante": {
    "id": "UUID do fabricante existente"
  },
  "localizacao": {
    "id": "UUID da localizacao existente"
  },
  "movimentacoes": [ ]
}
```

Exemplo de criação (referenciando IDs já existentes):

```bash
curl -X POST http://localhost:8080/api/reagentes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Soro Fisiológico 0,9%",
    "codigoSku": "SRF-009",
    "lote": "L2025-01",
    "dataValidade": "2026-01-31",
    "dataRecebimento": "2025-10-10",
    "quantidadeEmEstoque": 100,
    "status": "LIBERADO",
    "fabricante": { "id": "<UUID_FABRICANTE>" },
    "localizacao": { "id": "<UUID_LOCALIZACAO>" }
  }'
```

### Movimentações (`/api/movimentacoes`)

- GET `/api/movimentacoes` → 200 OK
- GET `/api/movimentacoes/{id}` → 200 OK | 404 Not Found
- POST `/api/movimentacoes/reagente/{reagenteId}` → 201 Created
- PUT `/api/movimentacoes/{id}` → 200 OK | 404 Not Found
- DELETE `/api/movimentacoes/{id}` → 204 No Content | 404 Not Found

Estrutura do `MovimentacaoDTO`:

```json
{
  "id": "UUID opcional",
  "dataHoraMovimentacao": "YYYY-MM-DDTHH:mm:ss",
  "tipo": "ENTRADA_NOTA|SAIDA_USO_ANALISADOR|...",
  "quantidadeMovimentada": 10,
  "observacao": "string opcional"
}
```

Exemplo de entrada de estoque para um reagente:

```bash
curl -X POST http://localhost:8080/api/movimentacoes/reagente/<UUID_REAGENTE> \
  -H "Content-Type: application/json" \
  -d '{
    "dataHoraMovimentacao": "2025-11-09T10:15:00",
    "tipo": "ENTRADA_NOTA",
    "quantidadeMovimentada": 20,
    "observacao": "Entrada via NF 12345"
  }'
```

## 🧪 Objetos de Domínio (DTOs)

Os principais DTOs expostos pela API:

- `FabricanteDTO`: `id`, `nomeOficial`, `nomeFantasia`, `cnpj`, `paisOrigem`
- `LocalizacaoDTO`: `id`, `codigoLocal`, `descricao`, `tipo`, `faixaTemperaturaNominal`, `setor`
- `ReagenteDTO`: `id`, `nome`, `codigoSku`, `lote`, `dataValidade`, `dataRecebimento`, `quantidadeEmEstoque`, `status`, `fabricante`, `localizacao`, `movimentacoes`
- `MovimentacaoDTO`: `id`, `dataHoraMovimentacao`, `tipo`, `quantidadeMovimentada`, `observacao`

## 📦 Códigos de Status (Resumos)

- 200 OK → sucesso nas operações de leitura e atualização
- 201 Created → recurso criado com sucesso (POST)
- 204 No Content → remoção realizada com sucesso (DELETE)
- 404 Not Found → recurso não encontrado

## ❗ Dicas e Solução de Problemas

- Erro de versão do Java: confirme que o `java -version` é 21.
- Portas ocupadas: ajuste `server.port` em `application.properties` (ex.: `server.port=8081`).
- Datas/Enums inválidos: verifique o formato ISO-8601 e use os valores exatos de enum definidos no código.

