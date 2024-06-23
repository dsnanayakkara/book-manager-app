# Library Management System API

## Introduction
This project is a RESTful API for a simple library management system. It allows users to register borrowers, register books, and manage book borrowing and returning. The API uses stateless authentication with JSON Web Tokens (JWT).

## Technical Stack

| Technology       | Version |
|------------------|---------|
| Java             | 17      |
| Spring Boot      | 3.3.0   |
| Spring Data JPA  | 3.3.0   |
| Spring Security  | 6.3.0   |
| MySQL            | 8.0     |
| Docker           | 26.1.4  |
| Docker Compose   | 2.27.1  |
| JWT              | 0.10.8  |
| JUnit            | 5.11.0  |
| Mockito          | 5.12.0  |


## Prerequisites
- Docker
- Git
- Postman (Optional for testing)

## Usage
1. Clone the project from the GitHub repository:
git clone https://github.com/dsnanayakkara/book-manager-app.git
Alternatively, you can download the project as a zip file and extract it.
2. Navigate to the root directory of the project in your terminal.
3. Run Docker Compose to start the containers for the application and MySQL database services:
- docker-compose up -d --build
- The application and MySQL database are containerized and managed with Docker Compose. The database and test data will be automatically created before starting the Spring Boot application.
4. You can now interact with the API using Postman or any other API client.
- /book-manager-app/docs folder contains a postman collection which can be used to call the endpoints and see result.

## Authentication
The API uses Spring Security for authentication. It issues JSON Web Tokens (JWT) to authenticated users. To authenticate, make a request to the ` /api/v1/auth/token` endpoint with your credentials to receive a token. Include this token in the `Authorization` header of your requests (in the format `Bearer <token>`).

## Database Design
A Relational database design was selected due to the relational nature of the data and the presence of numerous CRUD operations pertaining to a library management system.
![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/cb357987-1aee-4bd0-bf3f-e5b9ac3d90c8)
## Testing and Code Coverage
The project includes unit tests written with JUnit. You can run these tests to verify the functionality of the API. 
- /book-manager-app/docs folder contains a postman collection which can be used to call the endpoints and see result.

## Logging
- Log file is created inside the logs folder.
- Logs are written to both the console and log file.
- A RollingFileAppender is configured with max file size of 10MB

