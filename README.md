# Registers App
Registers App is a simple app to simulate bank accounts. It allows to simply transfer 'cash' between registries or recharge one.
Application was built with Spring boot initializer available here: https://start.spring.io/

###Preconditions:
- Java 11
- Maven 3.6+
- SQL Server database

### Build App
- App is using MSSQL database. You have to create database and set credentials in application.properties file.
- Run command: `mvn clean install`

### Run App
`mvn spring-boot:run`

###REST API
REST API can be tested with swagger UI:
http://localhost:8080/swagger-ui.html