# Agregador de Investimentos

API REST desenvolvida com Java e Spring Boot para gerenciamento de usuários, contas de investimento e ativos financeiros.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- OpenFeign
- Maven
- JUnit / Mockito

## Funcionalidades

- Cadastro de usuários
- Criação de contas de investimento
- Cadastro e consulta de ativos (Stocks)
- Associação de ativos às contas
- Integração com a API da BRAPI para obtenção de informações de mercado
- Testes automatizados

## Configuração

### Banco de Dados

Configure as propriedades do banco de dados no arquivo:

```properties
application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://://localhost:3306/agregadorinvestimentos
spring.datasource.username=mysql
spring.datasource.password=senha
```

### Token da BRAPI

Além da configuração do banco de dados, é necessário informar a variável de ambiente:

```env
TOKEN=seu_token_da_brapi
```

O token é utilizado para autenticação nas requisições realizadas para a API da BRAPI.

## Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/LucasAS07/agregador_investimento.git
```

Acesse a pasta do projeto:

```bash
cd agregador_investimento
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

## Executando os Testes

```bash
mvn test
```

## Objetivo do Projeto

Projeto desenvolvido para estudos de desenvolvimento de APIs REST utilizando Spring Boot, JPA/Hibernate, integração com APIs externas e testes automatizados.
