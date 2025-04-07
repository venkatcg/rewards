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


End Point :

POST /api/rewards

Request Body

[
  { "customer": "Alice", "amount": 120, "date": "2025-01-15" },
  { "customer": "Alice", "amount": 75, "date": "2025-01-25" },
  { "customer": "Bob", "amount": 200, "date": "2025-02-05" },
  { "customer": "Alice", "amount": 130, "date": "2025-03-10" }
]

Response 

{
    "Bob": {
        "total": 250,
        "2025-02": 250
    },
    "Alice": {
        "total": 225,
        "2025-01": 115,
        "2025-03": 110
    }
}
