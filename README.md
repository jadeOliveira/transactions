# Transactions


## Requisitos do projeto

- Gradle 6.6
- OpenJDK 11
- Docker

## Configurações do projeto

Caso seja necessário alterar as configurações do BD devido a alterações no docker-compose.yaml, editar o arquivo application.properties e alterar as propriedades abaixo:

```
spring.datasource.url=jdbc:mysql://localhost:3306/jademe
spring.datasource.username=root
spring.datasource.password=jademe
```
Se quiser customizar o arquivo compose/docker-compose.yml e configurar o volume para um diretório local:

```yaml
volumes:
    - /C/dev/docker/mysql/mysql_data:/var/lib/mysql
```
Executar o comando **docker-compose** através do make para subir o container:

```bash
make docker-compose-up
```

Todas as entidades necessárias são criadas automaticamente pela aplicação através de scripts do liquibase.

## Makefile:

Usar ``makefile help`` para verificar a lista de comandos disponíveis.


## Endpoints

- [Criar conta](documentation/post-account.md):  `POST /accounts`

- [Consultar conta](documentation/get-account.md):  `GET /accounts/:accountId`

- [Criar transação](documentation/post-transactions.md):  `POST /transactions`

  

