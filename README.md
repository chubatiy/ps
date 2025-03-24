# PSP Demo – Payment Service Platform

A production-ready simulation of a Payment Service Provider (PSP) built with Kotlin and Spring Boot. This service processes payments, routes them to simulated acquirers, and returns transaction results. The system uses PostgreSQL for persistence and RabbitMQ for internal communication.

---

## ⚙️ Features

- ✅ RESTful API for payment processing
- ✅ Luhn algorithm for card validation
- ✅ BIN-based routing logic
- ✅ PostgreSQL-backed transaction persistence
- ✅ RabbitMQ for asynchronous processing
- ✅ Docker-based deployment
- ✅ Clean architecture with modular structure

---

## 🧠 Architecture & Design Decisions

### System Overview

- **API Layer** – Handles incoming HTTP requests.
- **Service Layer** – Implements routing, validation, and business logic.
- **Persistence Layer** – Uses PostgreSQL to persist transaction data.
- **Messaging Layer** – Uses RabbitMQ to communicate with mock acquirers asynchronously.
- **Acquirers** – Simulated services that approve/deny transactions based on simple logic.

### BIN Routing Logic

Acquirer selection is based on the sum of the digits in the BIN (first 6 digits):
- **Even sum → Acquirer A**
- **Odd sum → Acquirer B**

Each acquirer approves or denies a transaction based on the last digit of the card number:
- **Even → Approved**
- **Odd → Denied**

---

## 📘 API Documentation

Swagger UI is available at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 📡 API Endpoints

### `POST /api/payments`

**Description:** Process a payment request.

#### Request Body
```json
{
  "cardNumber": "4111111111111111",
  "expiryDate": "12/25",
  "cvv": "123",
  "amount": 50.75,
  "currency": "USD",
  "merchantId": "MERCHANT-001"
}
```

#### Response
```json
{
  "transactionId": "a1b2c3d4",
  "status": "Approved",
  "message": "Processed by Acquirer A"
}
```

### `GET /api/payments/{id}`

**Description:** Retrieve details of a specific payment by ID.

#### Response
```json
{
  "transactionId": "a1b2c3d4",
  "status": "Approved",
  "message": "Processed by Acquirer A"
}
```

---

## 🧱 Technologies

- Kotlin + Spring Boot
- PostgreSQL
- RabbitMQ
- Docker + Docker Compose
- Maven

---

## ▶️ Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 17+
- Git

### Run via Docker

#### Standalone 
```bash
docker-compose up --build
```

#### Environment for local debug
```bash
./builder/docker-compose up
```

The application will be available at `http://localhost:8080`.

---

## 🐇 RabbitMQ Messaging

RabbitMQ is used to simulate asynchronous communication with acquirers. After validating and routing the payment, the app sends a message to the queue. A listener consumes it and simulates the acquirer's response, updating the transaction status accordingly.

---

## 🛡 Security Considerations (For Production)

While the current implementation is simplified for demonstration, in a real-world scenario:

- Encrypt sensitive data with **AES-256**
- Implement **Tokenization** or delegate PCI-sensitive operations to third parties
- Authenticate API requests using OAuth2 / JWT
- Store secrets in **Vault** or **AWS Secrets Manager**

---

## 🚀 Suggested Improvements for Production

| Area                | Recommendation                                                                 |
|---------------------|---------------------------------------------------------------------------------|
| **High Availability** | Use PostgreSQL clustering (e.g., Patroni) and RabbitMQ clustering              |
| **Messaging**        | Switch to Kafka with Outbox Pattern for at-least-once delivery                 |
| **Microservices**    | Extract acquirers as individual microservices with load balancing              |
| **Resilience**       | Add retry logic, circuit breakers (Resilience4j), and timeouts                 |
| **Observability**    | Integrate Prometheus + Grafana, OpenTelemetry for tracing                      |
| **Scalability**      | Deploy on Kubernetes with HPA and service meshes (Istio/Linkerd)               |
| **CI/CD**            | Use GitHub Actions or GitLab CI for automated build/test/deploy                |
| **Async APIs**       | Support webhooks or async APIs for transaction callbacks                       |
| **Rate Limiting**    | Add rate limits per merchant/client ID for DoS protection                      |

---

## 🧪 Running Tests

```bash
./mvnw test
```

Tests should include:
- Unit tests for validation and routing logic
- Integration tests for messaging and DB interactions
- Controller-level tests for REST contract validation