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
[![Slack bot example](https://user-images.githubusercontent.com/25894229/87185120-34459300-c2b7-11ea-910a-c4fc716ebdde.png)]()

### Endpoints

<dl>
  <dd>- POST ("/api/v1/account") - Create an account</dd><br>
  <dd>- POST ("/api/v1/customer") - Create a new customer</dd><br>
  <dd>- GET ("/api/v1/customer") - Find all customers</dd><br>
  <dd>- GET ("/api/v1/customer/{userId}") - Find customer by its Id</dd><br>
  <dd>- POST ("/api/v1/wallet/{customerId}") - Create a new wallet</dd><br>
  <dd>- POST ("/api/v1/wallet/{walletId}/account/{accountId}/withdraw/{amount}") - Withdraw from the account</dd><br>
  <dd>- POST ("/api/v1/wallet/{walletId}/account/{accountId}/deposit/{amount}") - Deposit to the account</dd><br>
  <dd>- POST ("/api/v1/wallet/{fromWalletId}/account/{fromAccountId}/transfer/wallet/{toWalletId}/account/{toAccountId}/amount/{amount}") - Transfer money from one account to another</dd><br>
  <dd>- GET ("/api/v1/wallet/{walletId}/account/{accountId}/balance") - Get an account balance</dd><br>
  <dd>- GET ("/api/v1/wallet/{walletId}/account/{accountId}/lastNTransactions/{n}") - Get last n transactions from the account</dd><br>
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
