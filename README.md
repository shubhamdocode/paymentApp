                                                 PAYMENT DEMO


This is a demo project bulti in Java 8 with SpringBoot and JPA Hibernate.

PREREQUISTES REQUIRED:

1. Install java version 8 or higher to run the jar file.
2. Install mysql 8.3.x version.
3. set mysql username =root and password=local123456 or you can change values in "src/main/resources/application.properties" files according to your credentials.
4. To Run project you can go to  "target/paymentDemo.jar"  use command :  java -jar paymentDemo.jar and project will be started on port 8081.



DATABASE SCHEMAS:

 1. Create Database transactiondb;
 2. CREATE TABLE `Accounts` (
  `Account_ID` int NOT NULL AUTO_INCREMENT,
  `Document_Number` bigint NOT NULL,
  PRIMARY KEY (`Account_ID`)
)
  3.  CREATE TABLE `OperationsTypes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `OperationType_ID` smallint NOT NULL,
  `Description` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
)

4. CREATE TABLE `Transactions` (
  `Transaction_ID` int NOT NULL AUTO_INCREMENT,
  `Account_ID` int NOT NULL,
  `Amount` decimal(15,2) NOT NULL,
  `OperationType_ID` tinyint NOT NULL,
  `EventDate` timestamp NOT NULL,
  PRIMARY KEY (`Transaction_ID`)
)

CURL API SCHEMAS:
  
  1. GET ->  curl --location 'http://localhost:8081/txn/accounts/1'
  2. POST ->  curl --location 'http://localhost:8081/txn/accounts' \
    --header 'Content-Type: application/json' \
    --data '{
   "document_number": "12345"
     }'

 3. POST -> curl --location 'http://localhost:8081/txn/transactions' \
        --header 'Content-Type: application/json' \
        --data '{
        "account_id": 1,
        "operation_type_id": 4,
        "amount": 10.20
        }'
     
NOTE: 

Transactions of type purchase and withdrawal are registered with negative amounts, while
transactions of credit voucher are registered with positive value.

OperationsTypes 


| OperationType_ID | Description    
| -------------    | -------------   
| 1                |  Normal Purchase
| 2                |  Purchase with installments 
| 3                |   Withdrawal
| 4                |   Credit Voucher









