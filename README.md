# SpendSmart

##  Overview

SpendSmart is a **microservices-based personal finance management system** designed to help users manage their expenses, income, budgets, and financial insights efficiently.

The system follows a **distributed architecture**, where each microservice is independently developed, deployed, and maintained. It ensures scalability, modularity, and ease of maintenance.

---

##  Architecture Overview

The application is built using a **microservices architecture pattern** with the following core components:

* API Gateway for centralized request handling
* Eureka Server for service discovery
* Independent microservices for each domain
* Database per service design
* JWT-based authentication for security

---

##  Authentication Flow

The system uses **JWT (JSON Web Token)** for secure communication:

1. User authenticates via Auth Service
2. A JWT token is generated
3. Token is sent with every request
4. Gateway validates the token
5. User identity is forwarded to downstream services

---

##  Microservices Description

###  Auth Service

The Auth Service is responsible for **user authentication and identity management**. It handles user registration, login, and secure password storage. Upon successful authentication, it generates a JWT token which is used across the system for secure access.

---

###  Gateway Service

The Gateway Service acts as a **single entry point** for all client requests. It is responsible for routing requests to appropriate services, validating JWT tokens, and forwarding user-related headers. This ensures centralized security and simplified communication.

---

###  Expense Service

The Expense Service manages all **expense-related operations**. It allows users to track their daily spending and organize expenses under different categories. Each expense is associated with a specific user.

---

###  Income Service

The Income Service handles **income tracking**. Users can record different income sources such as salary, freelance work, or other earnings. This service contributes to overall financial analysis.

---

###  Category Service

The Category Service allows users to create and manage **custom categories** for expenses and income. It helps in organizing financial data for better clarity and reporting.

---

###  Budget Service

The Budget Service enables users to define **spending limits** for specific categories. It helps users monitor their financial discipline and avoid overspending.

---

###  Analytics Service

The Analytics Service provides **financial insights and summaries**. It aggregates data from income and expense services to calculate total income, total expenses, and overall balance. It helps users understand their financial health.

---

###  Notification Service

The Notification Service is responsible for **alerting users**. It can notify users about important events such as budget limits being exceeded or other financial updates. Currently, it supports manual triggering.

---

###  Recurring Service

The Recurring Service manages **automated recurring transactions**. It simulates scheduled operations such as monthly subscriptions or recurring income. It interacts with other services to generate transactions automatically.

---

##  Database Design

Each microservice maintains its **own database**, following the principle of:

```text
Database per Service
```

This ensures:

* Loose coupling
* Independent scalability
* Better data isolation

---

##  Inter-Service Communication

Services communicate with each other using:

* **REST APIs**
* **OpenFeign (Declarative HTTP Client)**

This allows seamless data sharing between services like:

* Analytics → Expense & Income
* Recurring → Expense & Income

---

##  Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Cloud Gateway
* Eureka Server
* Spring Data JPA
* PostgreSQL
* OpenFeign
* JWT Authentication

### Frontend (Planned)

* Angular

---

##  Key Features

* Microservices Architecture
* Secure Authentication (JWT)
* Centralized API Gateway
* Service Discovery using Eureka
* Independent Databases per Service
* Financial Tracking (Income & Expense)
* Budget Management
* Analytics & Insights
* Notification System
* Recurring Transactions

---

##  Current Status

- Backend Services:  Completed
- Frontend:  In Progress

---
