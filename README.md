# 🧾 Virtual Card Service

This project is a simplified **Virtual Card Service** built in Java, following **Clean Architecture** and **Domain-Driven Design (DDD)** principles. It was designed to simulate the core backend functionality of a virtual card platform, where users can create cards and authorize transactions against their limits.

## 📚 Project Description

You're building a backend service for creating and managing virtual cards. Each card:

* Belongs to a specific user
* Has an associated limit (balance)
* Supports transactions with balance validation

This service exposes a few key endpoints for managing cards and authorizing payments.

## 🚀 Features

* ✅ Create virtual cards
* ✅ List all cards by user
* ✅ Authorize transactions
* ✅ Validate balance before authorizing
* ✅ Throw domain-specific exceptions (e.g., insufficient funds, card not found)
* ✅ In-memory persistence (for testing and learning purposes)
* ✅ Modular design using Clean Architecture and DDD
* ✅ Unit tested use cases

## 📦 Endpoints

| Method | Route             | Description                        |
| ------ | ----------------- | ---------------------------------- |
| POST   | `/cards`          | Create a new card                  |
| GET    | `/cards/{userId}` | List all cards belonging to a user |
| POST   | `/authorize`      | Authorize a transaction on a card  |

### 🧪 Transaction Rules

* If the card has **enough balance**, the transaction is **approved**, and the balance is reduced.
* If **not**, the transaction is **rejected** with a `LimitExceededException`.

## 🗂️ Architecture Overview

The project uses a **Clean Architecture** layered structure:

```
/src
  ├── domain          # Core business logic and models
  ├── application     # Use cases and service interfaces
  ├── delivery        # HTTP handlers / controllers
  ├── infrastructure  # In-memory repositories and adapters
```

## 🧱 Tech Stack

* Java 23
* SparkJava (lightweight HTTP framework)
* JUnit 5 for testing

## 🧠 Concepts Practiced

* Clean Architecture
* Domain-Driven Design (DDD)
* In-memory persistence
* Unit testing
* Dependency inversion
* Exception handling
* RESTful API design

## 📌 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/rodrigo-militao/virtual-card-service.git
   cd virtual-card-service
   ```

2. Build and run:

   ```bash
   ./gradlew run
   ```

3. Test:

   ```bash
   ./gradlew test
   ```

## ✅ Future Improvements

* Add integration tests
* Add persistence with PostgreSQL
* Use a DI framework (e.g., Guice or Spring)
* Replace SparkJava with Spring Boot for scalability
* Add Swagger/OpenAPI documentation
* Add Docker support
