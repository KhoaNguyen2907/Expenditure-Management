# Expenditure Management - Microservices Project
This project aims to implement an expenditure management system using microservices architecture. Users can input their daily expenses and incomes, then can get the report by day or months. The reports include total income, total expense, total balance, and all the trasaction in that day or month.

## Implementation
- Authenticator: Handles user authentication and authorization. It uses Spring Security with JWT filter to authenticate and authorize the users. It also store basic infomation of user.

- Tracker: Handles tracking of expenditure transactions. It stores the transaction details in a database and provides API for creating, updating, and deleting the transactions. Whenever a transaction is created, updated, or deleted in the Tracker microservice, it sends a notification to the Reporter microservice via a message broker (RabbitMQ).

- Reporter: Generates daily or monthly reports based on the transactions send from Tracker. Once revieced an notification of update or create transaction from tracker, Reporter immediately update reports base on that. Additional, it provide API to get user current balance.

- API-gateway: Acts as a single entry point for all the other microservices.

- Eureka server: used for service discovery and registration. All the microservices register themselves with the Eureka Server and the API Gateway uses the Eureka Server to discover the available microservices.

- Common-api: The Common API microservice provides common functionalities such as config, filter, service, utils and exception handling. All the other microservices use the Common API for these functionalities.

- AMQP : Uses the Advanced Message Queuing Protocol (AMQP) for message transfer between the microservices. Using this for centralized queue config.

- Nginx: Will be implemented to replace API-gateway and Eureka server.

## Demo
To test this locally, the following needs to be installed on current machine:

- JDK (used OpenJDK 11).
- Docker (used version 19).
- Docker Compose (used version 1.25.4).

After installing the requirements above, do the following:

1. Start all the services in Docker compose:
```bash
./docker compose up -d
```

2. Go to SwaggerUI for testing api:
```bash
 - user: http://localhost:8082/swagger-ui/index.html
 - tracker: http://localhost:8080/swagger-ui/index.html
 - reporter: http://localhost:8081/swagger-ui/index.html
 ```

3. Access database, rabbitmq, zipkip, api gateway and more:
 - See all address in
 ```bash
 docker-compose.yml
 ```
## Todo
nginx, centralized log, concurrency, cache, circuit breaker, monitoring, CI/CD, kubernetes, deployment etc.
