# 💰 Financeiro Pessoal API

Sistema de controle financeiro pessoal com autenticação segura, categorização de lançamentos, filtros avançados, monitoramento com Prometheus e dashboards no Grafana.

## 📌 Tecnologias

- Java 17 + Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Maven
- Prometheus & Grafana
- SonarCloud
- Docker e Docker Compose
- Railway (Deploy)

## ⚙️ Como rodar localmente

### ▶️ 1. Sem Docker

```bash
# Clone o projeto
git clone https://github.com/seu-usuario/FinanceiroPessoal.git
cd FinanceiroPessoal/backend

# Configure as variáveis de ambiente
cp .env.example .env

# Suba o banco local (via Docker ou localmente)
docker-compose up -d postgres

# Rode a aplicação
mvn spring-boot:run

```

🐳 2. Com Docker Compose
```bash
# Na raiz do projeto
docker-compose -f docker-compose.yml up --build
```

🧪 Como rodar os testes

# Dentro da pasta backend
```bash
mvn clean verify
```

### 📖 Acesso ao Swagger
Após iniciar a aplicação:

URL: http://localhost:3000/swagger-ui.html

Documentação OpenAPI: http://localhost:3000/v3/api-docs

### 📊 Monitoramento com Prometheus + Grafana

Prometheus: http://localhost:9090/targets

Grafana: http://localhost:3001

Usuário: admin

Senha: admin

# Dashboards:

Métricas da JVM

Requisições por segundo

Uso de CPU/Heap/GC

### 🚀 Deploy em Produção (Railway)
Serviços hospedados:

Backend: https://financeiropessoal-production.up.railway.app

Proxy Prometheus: https://proxy-production-xxxx.up.railway.app

Prometheus: https://prometheus-production-xxxx.up.railway.app

Grafana: https://grafana-production-xxxx.up.railway.app

Swagger: https://financeiropessoal-production.up.railway.app/swagger-ui/index.html

### 🔐 Segurança
Autenticação JWT com Spring Security

CORS configurado

Papel de usuário via tabela usuario_roles

Métricas protegidas com JWT (rota intermediária via proxy)

### 📁 Estrutura de Pastas

├── backend/              # API Spring Boot
├── grafana/              # Dockerfile para subir Grafana
├── monitoring/           # Prometheus config e Dockerfile
├── .github/workflows/    # CI Pipeline (build, sonar, deploy)
└── docker-compose.yml    # Infraestrutura local

### 📜 Licença
Este projeto está sob a licença MIT.

# ✅ **Parte 2: `/docs` com exemplos práticos**

Criamos agora a pasta `/docs` com exemplos reais de requisição em `curl` e `Postman`.

#### 📁 Estrutura sugerida:

```bash
/docs
├── auth
│ └── login.json
├── lancamentos
│ ├── criar.json
│ ├── listar.json
│ └── filtrar-por-categoria.json
└── categorias
└── listar.json
```

Cada arquivo pode conter algo assim:
// Exemplo: docs/auth/login.json

```json
{
  "method": "POST",
  "url": "http://localhost:3000/auth/login",
  "body": {
    "email": "admin@monitor.com",
    "senha": "123456"
  }
}
```