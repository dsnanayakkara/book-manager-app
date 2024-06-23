# book-manager-app
RESTful API that manages a simple library system

# Technical Stack
* Java 17
* Spring Boot 3.3.0
* Spring Data JPA
* MySQL
* Logback
* Junit 5
* Mockito
* MapStruct
* Maven
* Docker
* Git
* GitHub actions workflow(for CI/CD)
* Swagger (API Documentation)
* JWT (with jjwt library)
* Postman (API testing)

# Deployment
Prerequisites:
- Git
- Docker
- Docker Compose

# Application Set up:
1. Clone the GitHub repository (git clone https://github.com/dsnanayakkara/book-manager-app.git)
2. Open a terminal/command prompt in the project's root directory.
3. Run:
docker-compose up -d

This will:

- Download the necessary Docker images.
- Build the Spring Boot application into a Docker image.
- Create and start the database container.
- Create and start the application container.
- DB schema creation and test data population script sql script (1_create_tables.sql) located at  inside db-init folder

# How to test the APIs
- /book-manager-app/docs folder contains a postman collection which can be used to call the endpoints and see result.

# Database Design
A Relational database design was selected due to the relational nature of the data and the presence of numerous CRUD operations pertaining to a library management system.


![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/cb357987-1aee-4bd0-bf3f-e5b9ac3d90c8)

# API Specification
https://app.swaggerhub.com/apis-docs/dsnanayakkara/library-manager_api/1.0.0#/Books/get_api_v1_books


# Logging
- Log file is created inside the logs folder.
- Logs are written to both the console and log file.
- A RollingFileAppender is configured with max file size of 10MB