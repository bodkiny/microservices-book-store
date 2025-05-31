# Microservices Book Store

This repository contains the source code for a sample Microservices-based Book Store application.

![BookStore Microservices Architecture](https://github.com/sivaprasadreddy/spring-boot-microservices-course/raw/main/docs/bookstore-spring-microservices.png)

## Modules

* **catalog-service**:  
  Provides REST APIs for managing the catalog of products (books).  
  **Tech Stack:** Spring Boot, Spring Data JPA, PostgreSQL

* **order-service**:  
  Provides REST APIs for managing orders and publishes order events to the message broker.  
  **Tech Stack:** Spring Boot, Spring Security OAuth2, Keycloak, Spring Data JPA, PostgreSQL, RabbitMQ

* **notification-service**:  
  Listens to order events and sends notifications to users.  
  **Tech Stack:** Spring Boot, RabbitMQ

* **api-gateway**:  
  API Gateway to the internal backend services (catalog-service, order-service).  
  **Tech Stack:** Spring Boot, Spring Cloud Gateway

* **bookstore-webapp**:  
  Customer-facing web application for browsing the catalog, placing orders, and viewing order details.  
  **Tech Stack:** Spring Boot, Spring Security OAuth2, Keycloak, Thymeleaf, Alpine.js, Bootstrap

## Learning Objectives

* Building Spring Boot REST APIs
* Database Persistence using Spring Data JPA, Postgres, Flyway
* Event-Driven Async Communication using RabbitMQ
* Implementing OAuth2-based Security using Spring Security and Keycloak
* Implementing API Gateway using Spring Cloud Gateway
* Implementing Resiliency using Resilience4j
* Job Scheduling with ShedLock-based distributed Locking
* Using RestClient, Declarative HTTP Interfaces to invoke other APIs
* Creating Aggregated Swagger Documentation at API Gateway
* Local Development Setup using Docker, Docker Compose, and Testcontainers
* Testing using JUnit 5, RestAssured, Testcontainers, Awaitility, WireMock
* Building Web Application using Thymeleaf, Alpine.js, Bootstrap

## Local Development Setup

* Install Java 21. (Recommend using [SDKMAN](https://sdkman.io/) for [managing Java versions](https://youtu.be/ZywEiw3EO8A).)
* Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* Install [IntelliJ IDEA](https://www.jetbrains.com/idea) or any of your favorite IDEs
* Install [Postman](https://www.postman.com/) or any REST Client

## How to Run the Project

After installing all prerequisites listed in the Local Development Setup:

1. Clone this repository, or download all contents of the `deployment` directory.
2. Open a terminal/console in the root of the project (or the `deployment` directory if only downloading it).
3. Run the following command:

    ```sh
    task start
    ```

This will start all required services for the Book Store application.
