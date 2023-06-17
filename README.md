The backend for PySpringChat, created with Spring Boot.

## Technologies used
Java 17

Backend - Spring Boot

Database - PostgreSQL

Testing - JUnit 5, Mockito

Other - Project Lombok, Springdoc, Jackson

## Documentation
You can get the auto-generated json documentation on the /v3/api-docs endpoint.

You can also see documentation on this page:
https://casperr04.github.io/PySpringChat-Backend/index.html

## Getting Started
The PySpringChat backend is a Spring Boot application built using Maven.

Firstly, you will need to set up PostgreSQL, which can be downloaded from here.
https://www.postgresql.org/download/ 

* Create a database created pyspringchat
* In /src/main/resources, change the application.properties:
```
...
spring.datasource.url=jdbc:postgresql://localhost:5432/pyspringchat
...
spring.datasource.username=[YOUR USERNAME]
spring.datasource.password=[YOUR PASSWORD]
...
```

* You can also create a seperate application.properties file and set those fields with the packaged jar.

You also need the Java 17 SDK. 
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

## Running the API
You can run the application directly with
```
mvnw spring-boot:run
```
Or by packaging it and running it as a jar.
```
mvnw package
java -jar pyspringchat-0.0.1.jar
```

## Endpoints
The documentation for the endpoints can be seen on https://casperr04.github.io/PySpringChat-Backend/index.html.

To create an user, you will have to create one using ```/v1/auth/register```

```
{
  "username": "user",
  "password": "pass
}
```

You will receive a bearer token, that you can attach to each subsequent request to authenticate the current user.

The token will expire in a day by default, but that can be changed in the application.properties.

There are a couple of unauthenticated endpoints, that do not require for the user to be authenticated. Those are specified in the documentation.

## Websocket Endpoints
The documentation for the websocket endpoints, and how to connect to a channel will be specified here, once the documentation is finished.

## Configuration
You can configure the expiration date of the bearer tokens in the application.properties.

```
bearer_token_expiration_length=[SECONDS]
```


## Running Tests

To run tests, run the following command

```
mvnw test
```

## Issues and feedback
This is my first Spring Boot project of this size, it is guranteed that there will be issues.

Feel free to point those out in the githubs issue tracker, or contribute with a pull request.
## License

[MIT](https://choosealicense.com/licenses/mit/)

