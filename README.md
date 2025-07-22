# Sistema Financeiro Pessoal com Relatórios e Categorias Inteligentes 📈

API REST desenvolvida em Java com Spring Boot para controle financeiro pessoal. Permite ao usuário cadastrar receitas e despesas, categorizar lançamentos, filtrar por períodos e tipos, e gerar relatórios mensais.

---

## \:rocket: Funcionalidades (MVP)

* Cadastro de usuário (em fase futura)
* CRUD de lançamentos financeiros (receitas e despesas)
* CRUD de categorias
* Filtros por tipo, data, categoria
* Relatório mensal com somatório de receitas/despesas
* Upload de extrato (CSV) *(em fase futura)*

---

## \:wrench: Tecnologias utilizadas

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* PostgreSQL
* Lombok
* Bean Validation
* Swagger (OpenAPI)
* Maven
* Docker 

---

## \:file\_folder: Estrutura baseada em Clean Architecture

```text
src
└── main
    └── java
        └── com.financeiro.backend
            ├── domain
            │   ├── entity
            │   ├── enums
            │   ├── repositories
            │   └── specifications
            ├── application
            │   └── services
            ├── web
            │   ├── controllers
            │   ├── dtos
            │   ├── exceptions
            │   └── mappers
            └── infrastructure
                ├── persistence
                └── config
```

---

## \:chart\_with\_upwards\_trend: Diagrama da Arquitetura

```text
+-----------------------+
|     Controller (API)  |
+-----------------------+
           |
           v
+-----------------------+
|    DTOs & Mapper      |
+-----------------------+
           |
           v
+-----------------------+
|    Services (App)    |
+-----------------------+
           |
           v
+-----------------------+
|   Domain (Entities)   |
+-----------------------+
           |
           v
+-------------------+     +-------------------+
| Repository (JPA)  |<--->|  PostgreSQL DB     |
+-------------------+     +-------------------+
```

---

## \:floppy\_disk: Como rodar localmente

1. Clone este repositório:

```bash
git clone https://github.com/seu-usuario/FinanceiroPessoal.git
```

2. Navegue até a pasta do projeto:

```bash
cd FinanceiroPessoal
```

3. Configure o banco de dados PostgreSQL localmente (ou via Docker) e atualize o `application.yml`

4. Rode o projeto:

```bash
./mvnw spring-boot:run
```

5. Acesse a documentação Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## \:construction: Em desenvolvimento

* [x] Login com JWT
* [ ] Upload e leitura de extrato CSV
* [x] Geração de gráficos com dados financeiros
* [x] Docker Compose
* [x] Hospedagem na nuvem

---

## \:handshake: Contribuição

Pull requests são bem-vindos. Para grandes mudanças, abra uma issue primeiro para discutir o que você gostaria de alterar.

---

## \:page\_facing\_up: Licença

Este projeto está sob a licença MIT.

---

Criado com ❤️ por Fernando Lemos
