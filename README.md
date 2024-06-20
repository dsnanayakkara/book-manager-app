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
* GitHub actions workflow
* Swagger (API Documentation)

# Deployment
Prerequisites:
- Git
- Docker
- Docker Compose

Steps:
1. Clone the project from the GitHub repository: (git clone https://github.com/dsnanayakkara/book-manager-app.git)
2. Open a terminal at the root of the project.
3. Run Docker Compose to start the container: (docker-compose up -d --build)
4. The database and test data will be automatically created before starting the Spring Boot application. This is done using sql script(create_tables.sql inside db-init folder) and docker compose.
5.  Access the endpoints using postman(API Specification is linked below)

# Database Design
A Relational database design was selected due to the relational nature of the data and the presence of numerous CRUD operations pertaining to a library management system.


![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/cb357987-1aee-4bd0-bf3f-e5b9ac3d90c8)

# API Specification
https://app.swaggerhub.com/apis-docs/dsnanayakkara/library-manager_api/1.0.0#/Books/get_api_v1_books


# Logging
- Log file is created inside the logs folder.
- Logs are written to both the console and log file.
- A RollingFileAppender is configured with max file size of 10MB