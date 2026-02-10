# Product Catalog Service

## Overview

This service is made for a code assessment for JDriven. It is a real-time product catalog API.
The following functionalities are in the application:
- Storing and finding products
- Full-text search on product attributes
- Webhook for synchronization of product prices

---

## Table of contents

1. [Tech Stack](#tech-stack)
2. [Prerequisites](#prerequisites)
3. [Running the application locally](#running-the-application-locally)
4. [Running application fully containerized](#running-application-fully-containerized)
5. [API Contract view](#api-contract-view)
6. [Performance test](#quick-performance-test-for-the-full-text-search)

---

## Tech Stack

- Java 25
- Spring Boot 4
- Spring Data JPA / Hibernate
- PostgreSQL
- Redis
- Flyway for database migrations
- Testcontainers for integration tests
- Hexagonal architecture

---

## Prerequisites

- Java 25 installed
- Maven
- IDE preferred
- Docker or Podman (for local PostgreSQL + Redis containers, and the integration tests)

## Running the application locally
Before you can run this application locally you need some external application to run.
The compose file will need some environment variables that are secret. So in this repository create an .env file.
In that file you will determine the password and username you will use for the database.
```bash
in your .env file:
DB_USERNAME=<insertusername>
DB_PASSWORD=<insertpassword>
```

To run PostgreSQL and Redis you can do the following in Docker or Podman:
```bash
podman-compose up -d
```
```bash
docker-compose up -d
```

Next you can compile the application by doing the following:
```bash
mvn clean install
```

In your running setup you will need to set some environment variables that the application needs to login to PostgreSQL
```bash
DB_USERNAME=
DB_PASSWORD=
```
Now you are ready to run the application.

## Running application fully containerized
After you have build the application you can run it fully containerized. For that you will need to build the Dockerfile
with the following command:
```bash
podman build -t product-catalog-service:1.0 .
```
Then you can uncomment the service in the compose file and rerun the compose-up command.

## API Contract view
You can view the api of the application through the following url:
```bash
http://localhost:8080/swagger-ui/index.html
```

## Quick performance test for the full-text search
One of the requirements is that the full-text search needs to be less than a second. To test that really quick you can do:
```bash
hey -n 1000 -c 50 "http://localhost:8080/products/search?q=tosti"
```

## Improvements
Before this application can be used it needs to be improved a little bit further before it can be deployed.

- Every path needs to be tested
- Authentication/authorisation for the create product en sync prizes need to be enforced for example with OAuth or others
- Pipeline needs to be created, but application is prepared for pipeline with Dockerfile
- DDD and Hexagonal architecture needs to be reviewed and adjusted
- Code quality checks need to be checked
- Code needs to be further optimized for better performance
- OpenSearch or Elastic needs to be implemented instead of tsVector from Postgres for better future
- Flyway needs to be correctly configured
- Redis also need to better configured
- If requirements are clarified, events can be added for the synchronisation 
