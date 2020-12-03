# Url Shortener

A simple RestFul API to create, update and access Addresses.

## System Requirements
The App is dockerized so you only need docker daemon and docker-compose on your machine to run this code otherwise if someone wants to run without Docker, these are the requirements

 - `Java v11`
 - `Maven v 3.6.3`
 - `Postgres 12`

## Installation
### To Run tests:
- `docker-compose -f docker-compose-test.yml run tests`

### To Start the Application:

#### With docker:

##### To build:
`docker-compose build`

##### To Start the app:
- `docker-compose up -d`

#### Without Docker
Make sure your configurations matches with the application configurations in `application.properties` and `flyway.properties` and you have rabbitMQ and PSQL running separately

- `mvn clean package -DskipTests`
- `mvn spring-boot:run -Dspring.profiles.active=development`

## Available Urls on local Server
0. `GET localhost:8080/api/v1/addresses/` To View all Address
    - it accepts `search` as a query parameter with comma(`,`) separated queries (`search=lastName:[string],email:[string]`)
    - it performs `OR` search between multiple attributes
0. `POST localhost:8080/api/v1/addresses/` To Create a new Address
0. `PUT localhost:8080/api/v1/addresses/[addressId]`  to Update the Address
0. `PATCH localhost:8080/api/v1/addresses/[addressId]` To Update only selected fields 
0. `Delete localhost:8080/api/v1/addresses/[addressId]` to Delete the Address

## Constrains/Validation
0. All values can be null/empty except firstName just for the sake of test 
0. Email should be valid if provided
0. ZipCode should be max 10 length
