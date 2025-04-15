#  Customer Rewards Program API

This Spring Boot application calculates reward points for customers based on their purchase transactions over a 3-month period.  

###  Reward Rules

- **2 points** for every dollar spent over **$100** in a single transaction.
- **1 point** for every dollar spent between **$50 and $100**.
- No points for spending **$50 or less**.

> Example:  
> $120 purchase = (2 × $20) + (1 × $50) = **90 points**

---

##  Features

- Accepts a list of transactions via REST API.
- Calculates rewards **per customer**, **per month**, and **total**.
- Clean JSON response.
- Built with Spring Boot.

---

##  Technologies

- Java 17+
- Spring Boot 3
- Maven
- Jackson for JSON


---

##  Setup

### Prerequisites

- Java 17+
- Maven

### Build & Run

```bash
# Clone the project
git clone https://github.com/venkatcg/rewards.git
cd rewards

# Build
mvn clean install

# Run
mvn spring-boot:run


1) Customer Reward Summary
Get total and monthly reward points for a specific customer

GET /api/rewards/{customerId}

Request
GET http://localhost:8082/api/rewards/C1

Response

{
    "customerId": "C1",
    "customerName": "Alice",
    "monthlyPoints": {
        "JANUARY": 90,
        "MARCH": 250,
        "FEBRUARY": 25
    },
    "totalPoints": 365
}

2) Monthly Transactions and Points
Get all transactions and calculated points for a specific customer in a specific month

GET /api/rewards/{customerId}/month/{yyyy-MM}

GET http://localhost:8082/api/rewards/C2/month/2024-02

Response :

{
    "customerId": "C2",
    "month": "2024-02",
    "transactions": [
        {
            "amount": 130.0,
            "transactionDate": "2024-02-17",
            "points": 110
        }
    ]
}

3. Rewards for a Date Range
Get reward points for all customers within a specific date range

GET /api/rewards/range?from={yyyy-MM}&to={yyyy-MM}

GET http://localhost:8082/api/rewards/range?from=2024-01&to=2024-03

Response :

{
    "from": "2024-01",
    "to": "2024-03",
    "rewards": [
        {
            "customerId": "C1",
            "customerName": "Alice",
            "month": "JANUARY",
            "points": 90
        },
        {
            "customerId": "C1",
            "customerName": "Alice",
            "month": "MARCH",
            "points": 250
        },
        {
            "customerId": "C1",
            "customerName": "Alice",
            "month": "FEBRUARY",
            "points": 25
        },
        {
            "customerId": "C2",
            "customerName": "Bob",
            "month": "JANUARY",
            "points": 45
        },
        {
            "customerId": "C2",
            "customerName": "Bob",
            "month": "MARCH",
            "points": 10
        },
        {
            "customerId": "C2",
            "customerName": "Bob",
            "month": "FEBRUARY",
            "points": 110
        }
    ]
}

4) Get Transactions for a Customer in a Specific Month
GET /api/rewards/customer/{customerId}/month/{yearMonth}
Request
http://localhost:8082/api/rewards/C001/month/2024-02

response

{
    "customerId": "C001",
    "month": "2024-02",
    "transactions": []
}
