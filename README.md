# User Locator API
This API returns people who are listed as either living in the specified city, or whose current coordinates are within the specified distance of the city. The API is implemented as a Spring Boot REST API using Java 11 and is built using Gradle.

## Running the API

The API can be started using the following command:

java -jar user-locator-0.1.0.jar

Note that if there is a port conflict using 8080 then an alternative port can be used:

java -jar user-locator-0.1.0.jar --server.port=8083

The API can be accessed using:

http://localhost:8080/locator/users

The city and distance defaults to London and 50 miles respectively. Alternative parameters can be specified:

http://localhost:8080/locator/users?city=London&distance=12.5

## Spring REST docs

The API is documented using Spring REST docs. The HTML can be generated using the following command:

./gradlew asciidoctor

The index.html can be found under src/main/asciidoc and describes request parameters and response fields as well as error responses.

## Building

The API is built using the following command:

./gradlew build

## Running tests

Tests are run using the following command:

./gradlew test

## Assumptions

The current implementation of the ProximityCalc interface calculates the distance between co-ordinates using doubles. The assumption is made that double provides enough precision for the purposes of the API. If more precision was needed then BigDecimal would be a better solution. The ProximityCalc interface could also be implemented using external packages or Google APIs.

The current implementation of the CityLocationService interface only supports the single city of London. Entering another city will result in a NOT_FOUND error response. 

