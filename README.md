# Spring Boot Library Management System

A clean Spring Boot REST API for managing books, library members, and book loans.

## Tech stack
- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 in-memory database
- Maven

## Run locally

```bash
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

## Endpoints

### Books
- `GET /api/books` — list books
- `GET /api/books/{id}` — get a book
- `POST /api/books` — create a book
- `PUT /api/books/{id}` — update a book
- `DELETE /api/books/{id}` — delete a book

Example:

```json
{
  "title": "Design Patterns",
  "author": "Erich Gamma",
  "isbn": "9780201633610",
  "totalCopies": 4
}
```

### Members
- `GET /api/members`
- `GET /api/members/{id}`
- `POST /api/members`
- `PUT /api/members/{id}`
- `DELETE /api/members/{id}`

Example:

```json
{
  "name": "Priya Singh",
  "email": "priya@example.com"
}
```

### Loans
- `GET /api/loans` — all loans
- `GET /api/loans/active` — active loans
- `POST /api/loans/issue?bookId=1&memberId=1` — issue a book
- `POST /api/loans/{id}/return` — return a book

## Database
The project uses an H2 in-memory database for a zero-configuration demo. Data is reset when the application restarts.

H2 console: `http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:librarydb`

## Sample data
A few books and members are created automatically on startup so the API can be tested immediately.

## Production note
For real deployment, replace H2 with PostgreSQL/MySQL and change `ddl-auto` to a migration-based approach such as Flyway or Liquibase.
