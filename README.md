# Money Transfer API

A simple RESTful API for money transfers between accounts, built with Java, Spring Boot, and MySQL. Designed for demonstration and internal system integration (no authentication).

## Features
- Create accounts
- Transfer money between accounts (atomic, validated)
- Query transfer details
- MySQL persistence (via Docker)
- Integration tests and bash script for demonstration

## Design Decisions
- **No authentication**: Assumed internal system use.
- **Simple models**: `Account` uses `double` for balance, `Transfer` uses `BigDecimal` for accuracy.
- **Transactional transfers**: Ensures atomic debit/credit.
- **Spring Data JPA**: For easy persistence.
- **MySQL via Docker Compose**: Easy local setup.

## What Was Omitted
- No user authentication/authorization
- No advanced error handling or validation beyond basics
- No pagination or filtering for transfer listing
- No production hardening (e.g., input sanitization, logging)

## How to Run

### Prerequisites
- Docker & Docker Compose
- Java 17+ (for local build)

### 1. Start MySQL
```
docker-compose up -d
```

### 2. Build and Run the App
```
./mvnw clean package
java -jar target/*.jar
```

App will start on port 8081.

### 3. Run Integration Tests
```
./mvnw test
```

### 4. Demo with Bash Script
```
chmod +x run_integration_test.sh
./run_integration_test.sh
```

This will:
- Build the app
- Start it
- Use `curl` to create accounts and perform a transfer
- Print results

## API Endpoints

- `POST /accounts` — Create an account
- `POST /transfers` — Transfer money
- `GET /transfers/{id}` — Get transfer details

## Example Transfer Request
```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100.0
}
```

## Development
- Branch: `add-transfer-api` (feature/dev branch)
- Main branch: `main`
- Please commit after each major change

---

**Questions?** Open an issue or contact the maintainer. 