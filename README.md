# Embarrassingly Insecure Payment Gateway

This microservice serves a tiny and embarrassingly insecure payment gateway system using
[Adyen](https://docs.adyen.com/api-explorer/) as the payment provider, focusing on supporting credit card payments.

## How to use

### Setup

#### Run Tests
First, ensure all tests are passing. Execute the following:
```
./gradlew test
```

#### Build and Run
To run the microservice, export your Adyen credentials and execute:
```
export ADYEN_API_KEY="Your Adyen API Key"
export ADYEN_MERCHANT_ACCOUNT="Your Adyen merchant account"
./gradlew run
```
The microservice exposes a `/status` endpoint, try it:
```
curl localhost:8080/status
```
You can also try end-to-end connectivity with Adyen by retrieving the allowed payment methods:
```
curl localhost:8080/paymentMethods
```

## Architecture and Design

### Technologies
- Kotlin 1.9.0 (chaining Java 11 bytecode)
- Java 11 (required by Adyen Java API)
- Ktor for Server and Client 2.3.3
- Log4j for logging 2.17.2
- Adyen Java API Library 21.0.0

This project was created based on [Interaction-Driven Design](https://www.codurance.com/publications/2017/12/08/introducing-idd) approach.