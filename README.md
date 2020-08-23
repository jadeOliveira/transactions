# Pismo Transactions

Aplicação Spring Boot 2.3 para contas e transações.


## Requisitos do projeto

- Gradle 6.5
- OpenJDK 14.0.1

## Configurações do projeto

Para executar a aplicação em ambiente de desenvolvimento, configurar o arquivo application.properties conforme abaixo:

```
spring.datasource.url=jdbc:mysql://localhost:3306/pismo
spring.datasource.username=root
spring.datasource.password=pismo
```

Todas as entidades necessárias são criadas automaticamente pela aplicação através de scripts do liquibase.

### Build do projeto

```bash
gradle clean build
```

### Executar

```bash
gradle clean bootRun
```

### Construir executável

```bash
gradle clean bootJar
```



## Endpoints

- [Criar conta](documentation/post-account.md):  `POST /accounts`

- [Consultar conta](documentation/get-account.md):  `GET /accounts/:accountId`

  

