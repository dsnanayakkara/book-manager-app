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

## Testing with postman

- /book-manager-app/docs folder contains a postman collection which can be used to call the endpoints and see result.
  
Here is a test case we can run with postman to test the application flow.
1. Download the postman collection inside `/book-manager-app/docs` and import it into postman standalone application.
2. Get the auth token:
   - Select the `GET:/api/v1/auth/token` endpoint that returns a JWT token for authenticating the rest of the endpoints.
   - This endpoint itself is authenticated with basic auth that uses username and password.
   - It's required to provide the username and password details as seen in the screen shot. (Included in the postman project).
   - Run the endpoint and Copy the "token" attribute from the response.
3. Register Borrower:
   - Select the POST:/api/v1/borrower endpoint to create a borrower in the system.
   - Select Authorization type as "Bearer" and paste the token from step 1 in the token field.
   - Example payload:
     `{
    "name": "userOne",
    "email": "userOneexample.com"
     }`
   - Run the endpoint, it will create a new entry in the `borrower` table.
   - Response will include the newly registered borrowerId.
   - Example Response
     `{
    "id": 5,
    "name": "userOne",
    "email": "userOneexample.com"
     }`
4. Register a Book:
   - Select the `POST:/api/v1/book` endpoint.
   - Select Authorization type as "Bearer" and paste the token from step 1 in the token field.
   - Example payload
     `{
    "title": "The Lord of the Rings",
    "author": "Tolkien, John R. R",
    "isbn":"9788845292613"
      }`
   - Run the endpoint, it will create a new entry in the `Book` table.
   - Response will include the newly registered bookId.
   - Example Response `{
    "id": 4,
    "title": "The Lord of the Rings2",
    "author": "Tolkien, John R. R",
    "isbn": "9788845292611"
    }`
5. Get all books (with optional filters):
   - Select the `GET:/api/v1/book` endpoint
   - Select Authorization type as "Bearer" and paste the token from step 1 in the token field.
   - Optionally, provide values for the query parameters `available_only` and `unique_only`
   - `available_only` filters the result to contain available (i.e. Not borrowed) books only.
   - `unique_only` filters the result to contain books with distinct ISBN only. These two filters can be combined.
   - Run the endpoint, it will return a list of books (optionally) based on query parameters.
   - Example Response:
   `{
    "result": [
        {
            "id": 1,
            "title": "Book Title 1",
            "author": "Author 1",
            "isbn": "978-3-16-148410-0"
        },
        {
            "id": 2,
            "title": "Book Title 2",
            "author": "Author 2",
            "isbn": "978-1-40-289460-1"
        }
    ],
    "totalBooks": 2
 }`
6. Borrow a book:
   - Select the `POST:/api/v1/book/{bookId}/borrow` endpoint.
   - Select Authorization type as "Bearer" and paste the token from step 1 in the token field.
   - Provide a book id representng an available book for the path parameter `bookId`, this can be retrieved from the step 4. Get all books endpoint with the filter 'available_only`
   - Run the endpoint, it will create a new `borrow_record` entry in the system and return the created resource id.
   - Payload should include the `bookId`, `borrowerId` and the `isbn`
     `{
    "bookId": 3,
    "borrowerId": 3,
    "isbn":"9781573226123"
     `}
   - Response includes the borrowed bookId, borrwerId and the borrowed timestamp.
     `{
    "bookId": 3,
    "borrowerId": 3,
    "borrowDate": "2024-06-23T08:48:21",
    "returnDate": ""
     }`
   - Result can be verified through the `GET:/api/v1/book` with `available_only = true`. Borrowed book will not be shown in the result.
7. Return a book:
   - Select the /api/v1/book/{bookId}/ endpont.
   - Select Authorization type as "Bearer" and paste the token from step 1 in the token field.
   - Provide a book id representng an already borrowed book for the path parameter `bookId`, this can be retrieved from the step 5 response.
   - Payload should include the `bookId`, `borrowerId` and the `isbn`
     `{
    "bookId": 3,
    "borrowerId": 3,
    "isbn":"9781573226123"
     }`
   - Response includes the borrowed `bookId`, `borrowerId`, `borrowDate` and `returnDate`.
     `{
    "bookId": 3,
    "borrowerId": 3,
    "borrowDate": "2024-06-23T08:48:21",
    "returnDate": "2024-06-23T08:51:10"
     }`
   - Result can be verified through the `GET:/api/v1/book` with `available_only = true`. Now it will show the return book back in the result as it's no longer unavailable.

Here is a screenshot of the Postman Project:
![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/5c8baa7f-e7ac-4dec-99f5-609ef9c52616)

## Database Design
A Relational database design was selected due to the relational nature of the data and the presence of numerous CRUD operations pertaining to a library management system.
![image](https://github.com/dsnanayakkara/book-manager-app/assets/47851416/cb357987-1aee-4bd0-bf3f-e5b9ac3d90c8)

## Unit tests and Code Coverage
The project includes unit tests written with JUnit. You can run these tests to verify the functionality of the API. 

## Logging
- Log file is created inside the logs folder.
- Logs are written to both the console and log file.
- A RollingFileAppender is configured with max file size of 10MB

