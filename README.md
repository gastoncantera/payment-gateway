# Embarrassingly Insecure Payment Gateway

This microservice serves a tiny and embarrassingly insecure payment gateway system using
[Adyen](https://docs.adyen.com/api-explorer/) as the payment provider, focusing on supporting credit card payments.

## Setup

### Requirements
This project requires Java 11 (or higher) JDK installed.
Although it is built with Kotlin (which produces Java 8 compatible bytecode), it depends on the [Adyen Java API Library](https://github.com/Adyen/adyen-java-api-library) which requires Java 11 or higher.

### Run Tests
First, ensure all tests are passing. Execute the following:
```
./gradlew test
```

### Build and Run
To run the microservice, export your Adyen credentials and execute:
```
export ADYEN_API_KEY="Your Adyen API Key"
export ADYEN_MERCHANT_ACCOUNT="Your Adyen merchant account"
./gradlew run
```

#### Connectivity Checks
The microservice exposes a `/status` endpoint, try it:
```
curl localhost:8080/status
```
You can also try end-to-end connectivity with Adyen by retrieving the allowed payment methods:
```
curl localhost:8080/payment/methods
```

## How to use

The microservice exposes 5 endpoints:

| Method | Endpoint          | Description                                 |
|--------|-------------------|---------------------------------------------|
| GET    | /status           | Get microservice status                     |
| GET    | /payment/methods  | Get Adyen list of available payment methods |
| POST   | /payment/checkout | Make a direct credit card payment           |
| POST   | /wallet/add       | Add a credit card to the wallet             |
| POST   | /wallet/checkout  | Make a payment using a card from the wallet |

### Adding Credit Card to Wallet
The card details will be validated and then stored in wallet.
Note that for this MVP, the **Wallet ID** and **Card ID** will be handled manually and if they don't exist, they will be created.
```
curl -X POST "localhost:8080/wallet/add" -H 'content-type: application/json' -d '
{
    "wallet_id": "1",
    "card_id": "1",
    "card_details": {
        "holder_name": "John Smith",
        "card_number": "4111111111111111",
        "expiry_month": "03",
        "expiry_year": "2030",
        "security_code": "737"
    }
}'
```

### Make a Payment using the Wallet
You have to specify the **Wallet ID** and **Card ID** along with the security code and the amount.
```
curl -X POST "localhost:8080/wallet/checkout" -H 'content-type: application/json' -d '
{
    "wallet_id": "1",
    "card_id": "1",
    "security_code": "737",
    "amount": {
        "currency": "EUR",
        "value": 1000
    }
}'
```

### Make a Direct Payment
You can make a payment directly providing the credit card details. The data will be validated and the payment will proceed.
```
curl -X POST "localhost:8080/payment/checkout" -H 'content-type: application/json' -d '
{
    "card_details": {
        "holder_name": "John Smith",
        "card_number": "4111111111111111",
        "expiry_month": "03",
        "expiry_year": "2030",
        "security_code": "737"
    },
        "amount": {
        "currency": "EUR",
        "value": 1000
    }
}'
```

### Transaction Logs
Logs to track credit card payment attempts and their results are stored in _transaction.log_
```
[INFO ] 2023-08-14 12:36:49.218 - WALLET TRX: CARD: ************1111 | BRAND: visa | AMOUNT: EUR 1000 | RESULT: Authorised (AUTHORISED)
[INFO ] 2023-08-14 12:37:01.020 - WALLET TRX: CARD: ************1111 | BRAND:  | AMOUNT:  | RESULT: Refused (DECLINED CVC Incorrect)
[INFO ] 2023-08-14 12:37:08.492 - DIRECT TRX: CARD: ************1111 | BRAND: visa | AMOUNT: EUR 1000 | RESULT: Authorised (AUTHORISED)
[INFO ] 2023-08-14 12:37:24.552 - DIRECT TRX: CARD: ************1111 | BRAND:  | AMOUNT:  | RESULT: Refused (DECLINED Expiry Incorrect)
```

## Architecture and Design

### Design decisions
- There is no Wallet maintenance endpoints.
  - The Wallet will be created by adding a Credit Card details.
  - Wallet ID and Card ID are simple _Strings_ in this MVP.
- Wallets and Credit Card details are stored in memory.
  - This can be improved by implementing the _CreditCardWalletRepository_ interface with a database with encryption at rest.
- There are 2 implementations for Adyen API connectivity:
  - _ApiAdyenPaymentService_: Based on Adyen Java API Library (default)
  - _HttpAdyenPaymentService_: Based on HTTP Adyen Endpoints
- This project was created based on [Interaction-Driven Design](https://www.codurance.com/publications/2017/12/08/introducing-idd) approach.
  - This includes packages structuring in a non-conventional way.
  - A design style that helps create maintainable and domain-oriented software.

### Technologies
- Kotlin 1.9.0 (chaining Java 11 bytecode)
- Java 11 (required by Adyen Java API)
- Ktor for Server and Client 2.3.3
- Log4j for logging 2.17.2
- Adyen Java API Library 21.0.0