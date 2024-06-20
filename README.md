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

# Deployment
* Clone the project from the github repository
* git clone https://github.com/dsnanayakkara/book-manager-app.git
* Open a terminal at the root of the project and run docker compose to start the container
* docker-compose up -d --build
* DB and test data will be automatically created before starting the spring boot application
* Access the endpoints using postman(API Specification provided as documentation)

# Database Design

![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/cb357987-1aee-4bd0-bf3f-e5b9ac3d90c8)

# API Specification
https://app.swaggerhub.com/apis-docs/dsnanayakkara/library-manager_api/1.0.0#/Books/get_api_v1_books
