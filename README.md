# Money Transfer API (Student Project)

This is a simple project regarding building REST APIs with Java and Spring Boot. The idea is to let you create accounts and send money from one account to another. I used MySQL for the database and Docker to make it easier to run.

## What it does
- You can create accounts 
- You can send money from one account to another (as long as there's enough money)
- You can look up a transfer by its ID

## Why I made it this way
- I didn't add any login or security for simplicity.
- I used Spring Boot and JPA because they make it easier to work with databases.
- I used Docker for MySQL so I didn't have to install it myself.
- I tried to keep the code as short and clear as possible.

## What I didn't do
- No authentication (so anyone can use it if they know the endpoints)
- No fancy error messages, just basic ones
- No extra features like listing all transfers or accounts
- Not really ready for real-world use, just for learning

## How to run it

### What you need
- Docker (for the database)
- Java 17 or newer
- Maven (if the Maven wrapper does not work)

### Steps
1. Start the MySQL database:
   ```
   docker-compose up -d
   ```
2. Build and run the app:
   
   **Using Global Maven:**
   - On any system:
     ```
     mvn clean package
     java -jar target/*.jar
     ```

   The app runs on port 8081.
3. You can run the tests with:
   ```
   mvn test
   ```
4. Or you can try the bash script I wrote to show how it works:
   ```
   chmod +x run_integration_test.sh
   ./run_integration_test.sh
   ```
   This will build the app, start it, and use curl to make some example requests.

## API Endpoints (the URLs you can use)
- `POST /api/accounts` — Make a new account
- `POST /transfers` — Send money from one account to another
- `GET /transfers/{id}` — See details about a transfer

### Example transfer request
```
POST /api/accounts
{
  "id": 1,
  "name": "Alice",
  "balance": 200.0
}
```

```
POST /transfers
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100.0
}
```

## About the code
- I worked on the `add-transfer-api` branch for this feature.
- The main branch is called `main`.
- I tried to commit after every big change.

---

