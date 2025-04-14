# Credit Card Transactions Pismo

This repository manages credit card transactions, including account and transaction management. It includes RESTful APIs for operations and is built using Java, Gradle, Docker, and MySQL.

---

## **Environment Requirements**
- **Java 17**
- **Gradle**
- **Docker**
- **MySQL**

---

## **Execution Instructions**
Mac terminal command
```bash
chmod +x run.sh
./run.sh
```

Windows terminal commands
```bash
./run.sh  (Note: ensure this is being run in git bash)
```

---

## **API Endpoints**

Swagger URL: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
![image](https://github.com/user-attachments/assets/e442072b-3ef3-4d8d-87d1-3463df80d20f)

### **AccountController**
- **Retrieve Account Details**
  ```http
  GET localhost:8080/api/account/details/{id}
  ```
  Retrieves details of the specified account.

- **Create a New Account**
  ```http
  POST localhost:8080/api/account/create
  ```
  **Request Body Example**:
  ```json
  {
    "documentNumber": "123456708911",
    "description": "This is a test value"
  }
  ```
  **Note:** 
  - `documentNumber` is required and must only contain numeric values.
  - Alphanumeric or special characters are not allowed in the `documentNumber`.

---

### **TransactionController**
- **Retrieve Specific Operation Type**
  ```http
  GET localhost:8080/api/transaction/operations/type/{id}
  ```
  Fetches a specific type of operation.

- **Retrieve All Operations**
  ```http
  GET localhost:8080/api/transaction/operations
  ```
  Fetches a list of all available operations.

- **Create a New Transaction**
  ```http
  POST localhost:8080/api/transaction/create
  ```
  **Request Body Example**:
  ```json
  {
    "documentNumber": "123456708911",
    "description": "This is a test value"
  }
  ```
  **Note:** 
  - A valid account is required to create a transaction.

- **Retrieve Specific Transaction**
  ```http
  GET localhost:8080/api/transaction/search/{id}
  ```
  Fetches details of a specific transaction.

---

## **Future Features and Enhancements**
1. **Event-Driven Architecture**: 
   - For larger-scale deployments, an event-driven system would be ideal for scaling and handling asynchronous workflows.

2. **Caching**:
   - Explore implementing caching mechanisms.
   - Decide between in-memory cache or standalone solutions like Redis based on the future roadmap.

3. **Ledger Implementation**:
   - Generate a double-entry ledger for transactions within a specified date range.
   - Provide a downloadable CSV format for the ledger.

---

ReadMe file generator:https://www.makeareadme.com
