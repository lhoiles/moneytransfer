#!/bin/bash

set -e

# Build the fat JAR
./mvnw clean package -DskipTests
JAR_FILE=$(ls target/*jar | grep -v 'original' | head -n 1)

# Start the Spring Boot app in the background
java -jar "$JAR_FILE" &
APP_PID=$!

# Wait for the app to be ready
until curl -s http://localhost:8081/actuator/health | grep UP; do
  echo "Waiting for app to start..."
  sleep 2
done

# Create two accounts
curl -X POST -H "Content-Type: application/json" -d '{"id":1,"name":"Alice","balance":200.0}' http://localhost:8081/accounts
echo
curl -X POST -H "Content-Type: application/json" -d '{"id":2,"name":"Bob","balance":50.0}' http://localhost:8081/accounts
echo

# Perform a transfer
curl -X POST -H "Content-Type: application/json" -d '{"fromAccountId":1,"toAccountId":2,"amount":100.0}' http://localhost:8081/transfers
echo

# Get transfer by id (assuming id=1)
curl -X GET http://localhost:8081/transfers/1
echo

# Kill the app process
kill $APP_PID 