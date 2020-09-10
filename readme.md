# my-bank-account

Java Spring Boot project with H2 in memory database that simulates bank account functionality.  
JPA(Java Persistence API) is used to interact with the in memory database.

#### This application allows users to do the following: 

<ul>
    <li>Add a new account to customer</li>
    <li>Create a new wallet for a customer</li>
    <li>Return an account balance </li>
    <li>Withdraw money from the account </li>
    <li>Deposit money to the account</li>
    <li>Transfer money from one account to another</li>
    <li>Return last # of transactions from the account</li>
</ul>

### UML Diagram
![UML](https://user-images.githubusercontent.com/25894229/92818795-1a4b2e00-f396-11ea-9180-7eb888cf1a27.png)

### Endpoints

<dl>
  <dt>- POST ("/api/v1/account") - Create an account</dt>
  <dt>- POST ("/api/v1/customer") - Create a new customer</dt>
  <dt>- GET ("/api/v1/customer") - Find all customers</dt>
  <dt>- GET ("/api/v1/customer/{userId}") - Find customer by its Id</dt>
  <dt>- POST ("/api/v1/wallet/{customerId}") - Create a new wallet</dt>
  <dt>- POST ("/api/v1/wallet/{walletId}/account/{accountId}/withdraw/{amount}") - Withdraw from the account</dt>
  <dt>- POST ("/api/v1/wallet/{walletId}/account/{accountId}/deposit/{amount}") - Deposit to the account</dt>
  <dt>- POST ("/api/v1/wallet/{fromWalletId}/account/{fromAccountId}/transfer/wallet/{toWalletId}/account/{toAccountId}/amount/{amount}") - Transfer money from one account to another</dt>
  <dt>- GET ("/api/v1/wallet/{walletId}/account/{accountId}/balance") - Get an account balance</dt>
  <dt>- GET ("/api/v1/wallet/{walletId}/account/{accountId}/lastNTransactions/{n}") - Get last n transactions from the account</dt>
</dl>

### Built With

<ul>
    <li>Java 11</li>
    <li>Java Spring Boot</li>
    <li>H2 Database</li>
    <li>Java Persistence API</li>
</ul>

### How to use this app

Please run the following commands:
<ul>
    <li>git clone {thisRepo}</li>
    <li>mvn clean install</li>
    <li>mvn spring-boot:run</li>
</ul>

Open http://localhost:8080 to run the app

Open http://localhost:8080/h2-console/ to check the database. Please use the following JDBC URL: 
<ul>
    <li>JDBC URL : jdbc:h2:mem:testdb</li>
</ul>
