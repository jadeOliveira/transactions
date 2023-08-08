# Transactions


## Requisitos do projeto

- Gradle 8.2
- OpenJDK 17
- SpringBoot 3.1.2
- Docker

Executar o comando **docker-compose** através do make para subir o container:

```bash
make docker-compose-up
```
Todas as entidades necessárias são criadas automaticamente pela aplicação através de scripts do liquibase.

## Makefile:

Usar ``makefile help`` para verificar a lista de comandos disponíveis.


## Endpoints

| ** HTTP Method ** | **Endpoint**                                                                             | Description                                   
|:------------------|:-----------------------------------------------------------------------------------------|:----------------------------------------------|
| POST              | [/accounts](documentation/post-account.md)                                               | Criar conta                                  |
| GET               | [/accounts/:accountId](documentation/get-account.md)                                     | Consultar conta                               |
| POST              | [/transactions](documentation/post-transactions.md)                                      | Criar transação                               |

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