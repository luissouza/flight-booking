<h1 align="center">
  <br>
   <img width="1000" src="https://i.postimg.cc/4yhf3skq/Captura-de-Tela-2023-01-14-a-s-01-01-59.png" alt="Logo ArrayMixer" title="Logo ArrayMixer by  cliparteles ( https://openclipart.org/user-detail/cliparteles )" />
  <br>
</h1>

<p align="center">
  This repository contains an example of a rest project developed in Java with SpringBoot and with the integration of other tools. This project addresses good practices, the implementation of layered architecture, the adoption of good practices for exception handling, the adoption of solid principles, and others. the purpose of this project is to apply good practices, implement good structures, test new java updates and other tools.
</p>

***

## Tools and plugins used in this project

* Java JDK 17.0.5
* Spring Boot 2.70
* Maven 3.87
* Cucumber 6.10.2
* Lombok
* Postgres
* Docker
* OpenApi 1.62
* EhCache 3.8.1
* Slfj 1.7.36
* Developed with: IntelliJ IDEA 2022.3 (Ultimate Edition)

*******

## Swagger Documentation

* Check the Swagger documentation on browser: http://localhost:8080/swagger-ui/index.html
* If you need, you can get a [Postman Collection] to help the execution of the tests.

*******

## How FlightBooking works?

* FlightBooking will analyze all searched flights by period, destination, currency and airports. After
  carrying out the search, the API will calculate the average price of all flights and the average
  price of baggages. All the flights data will be provided by skyPicker apis:

- **Get Flights API:** https://api.tequila.kiwi.com/v2/search
- **Get Airport Locations API:** https://api.tequila.kiwi.com/locations/

* Internally, the logic is executed after getting the flights. The first step is to group the
  flights by destination, after that go through all the flights and calculate the average price of
  all the flights, and finally go through all the baggage costs (baggage one and baggage two) and
  calculate the average price of each one.
  After executing all the logic, a record will be stored in the database history

*******

## Architecture I: Clean Architecture

<p align="center">
<img width="1000" height="600" src="https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg">
</p>

### Domain:

* This is the central layer of our application and the most important one, it is the closest to the application's business rules, that is, and when defined, it does not undergo as many changes as in the other layers and is still independent of all the other layers.
  In this layer will be the classes responsible for saying which actions the user can perform in the system (UseCases), such as buying a product or even canceling an order.
  In this layer will also be the entities. Entities are a representation of the application's business objects.

### Data:

* This layer is responsible for the communication between our application and the outside world, for example, communication with an API or a database.
  Repositories: this layer is the implementation of abstract repositories, contained in another layer, the implementation performs operations such as searching, removing, updating or inserting data between our application and the datasources layer, thus making the midfield between the two extremes.
  Models: are mirrors of entities, but may contain methods, such as conversion of input and output data, hash code and others.
  DataSources: Will perform HTTP GET requests on API or simply cache data using sqlite database.

### Core:

* The core layer contains utility classes, such as configuration and exception classes.

### Project Structure:
<p align="center">
<img width="450" height="450" src="https://i.postimg.cc/vZRWm060/Captura-de-Tela-2023-01-15-a-s-02-47-41.png">
</p>

*******

## Architecture II: The Three Tier (Three Layer) 

<p align="center">
<img width="1000" height="600" src="https://i.postimg.cc/J4bChn7q/1-x-A9-Zq-Dz-T3-yf-Zfur3-GW61-A.png"
  alt="Size Limit comment in pull request about bundle size changes"
  >
</p>

*******

### Controller Layer:

* It will contain Rest APIs definition and request body.
* Only API calls should invoke the Controller Layer.

### Service Layer:

* It will only take the data from the controller layer and transfer it to the repository layer.
* It will also contain business logic and model the data for the repository layer.
* It will also take the data from the repository layer and send it back to Controller Layer.
* Only the Controller layer should invoke the Service Layer.

### Repository Layer:

* It will interact with the underlying database.
* The service Layer should only invoke the repository layer.

*******

## Few Important Terminologies

* There are a few terminologies and concepts you should familiarize yourself, before going to the
  next section.

### DTO

* DTOs (Data Transfer objects) are the objects or classes used to transfer data between layers
  through the service layer.
* The service layer only accepts data in the form of DTOs.
* Any data returned to the controller layer will be in the form of DTOs.

### Model

* Models are the object used by the repository layer to call the Database stored procedure or
  perform CRUD operations without using Stored Procedure.

### Mapper

* Mappers are used to converting the form of data when transferred between layers. There are two
  types of Mappers:

* Model Mapper: This will map any data to the Model.
* DTO Mapper: This will map any data to DTOs.

*******

## SOLID Principles

* Dependency Inversion Principle ->  Code for abstraction not for implementation (applied in the service and repository layer for instance)

* Single Responsibility principle -> Lombok to remove java verbose for common tasks (repetitive code); usage of pattern dto – entity (never expose database contract to outside)

*******

## Error Handling

### The Importance of Api Exception Handling

* It is important to control exception handling so we can properly map exceptions to the ApiError
  object and inform API clients appropriately. Additionally, we would need to create more handler
  methods (the ones with @ExceptionHandler) for thrown exceptions within the application code.

<p align="center">
<img width="1000" height="600" src="https://i.postimg.cc/pLKhbFv8/Captura-de-Tela-2023-01-13-a-s-20-23-07.png"
  alt="Size Limit comment in pull request about bundle size changes"
  >
</p>

<p align="center">
<img width="1000" height="600" src="https://i.postimg.cc/MHfHN19C/Captura-de-Tela-2023-01-14-a-s-00-44-15.png"
  alt="Size Limit comment in pull request about bundle size changes">
</p>


### Exception response example:

```sh
      {
        "timestamp": "2023-01-15T10:37:14.301677",
        "httpCode": 500,
        "message": "Internal Server Error",
        "detail": "The flight codes is invalid. Please insert two airport codes separated by commas, example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. Consult the link: https://airportcodes.aero/iata/ and see if the codes are valid.."
      }
```

*******

## Running this project

1. Generate the project .jar file running the command:

    ```sh
      mvn clean install -DskipTests=true
    ```

2. Enter in the root of the project and run the project image with the command above:

   ```sh
     docker compose up
    ```

3. Consume the api: /api/v1/flights/avg, informing the parameters below:

   Example: http://localhost:8080/api/v1/flights/avg?flyTo=LIS,OPO&currency=GBP&dateFrom=2023-03-01&dateTo=2023-03-02
   #### Parameters:
   ```sh
      flyTo = Inform the desired destination of the flight, it is possible to enter only one destination or more than one separated by a comma, 
      Example: OPO (only Porto), LIS (only Lisbon), OPO,LIS (Porto and Lisbon)
      
      currency = The currency which the cost of the flight will be calculated.
      Example: EUR (Euro), GBP (British Pounds Sterling)
      
      dateFrom = Flight departure date
      Example: 2023-03-01
   
      dateTo = Flight arrival date
      Example: 2023-03-02
   
      
    ```

   The api will generate a average response like this:
     ```sh
         {
                "date_from": "01/03/2023",
                "date_to": "02/03/2023",
                "average_flights": {
                    "LIS": {
                        "city_name": "Lisbon",
                        "airport_name": "Portela Airport",
                        "currency": "GBP",
                        "price_average": 69.45,
                        "bags_price": {
                            "bag_one_average_price": 44.66,
                            "bag_two_average_price": 110.34
                        }
                    },
                    "OPO": {
                        "city_name": "Porto",
                        "airport_name": "Francisco Sá Carneiro Airport",
                        "currency": "GBP",
                        "price_average": 62.28,
                        "bags_price": {
                            "bag_one_average_price": 46.83,
                            "bag_two_average_price": 129.37
                        }
                    }
                }
         }      
     ```

4. Consume the api: /api/v1/flight/records to show all flight records in history
   Example: GET http://localhost:8080/api/v1/flight/records


5. Consume the api: /api/v1/flight/records to delete all flight records in history
   Example: DELETE http://localhost:8080/api/v1/flight/records

*******

## Information that can help you

- **Airlines API:** https://api.skypicker.com/airlines
- **Airport code:** https://airportcodes.aero/iata/


|IATA-Code |ICAO-Code  | Prefix-Code| Airline | Country
|--- | --- | --- |  --- |  ---|
|TP| TAP| 047 |TAP Air Portugal  | Portugal |
|FR | RYR| -- |Ryanair  | Ireland |

*******

## Test results

### Integrated tests - Cucumber

* the result of the automated tests can be accessed visually through the link generated after
  running the tests, similar to the example below:

<p align="center">
<img src="https://i.postimg.cc/T2z3XXxQ/Captura-de-Tela-2022-05-27-a-s-18-49-37.png"
  alt="Size Limit comment in pull request about bundle size changes"
  width="486" height="139">
</p>

* the results will have a display as the image below:

<p align="center">
<img src="https://i.postimg.cc/tRk3N4JP/Captura-de-Tela-2023-01-09-a-s-09-12-43.png"
  alt="Size Limit comment in pull request about bundle size changes"
  width="686" height="289">
</p>

### Unit tests - JUnit

* the result of the unit tests bellow:

<p align="center">
<img src="https://i.postimg.cc/nzCyGhf1/Captura-de-Tela-2023-01-09-a-s-09-53-41.png"
  alt="Size Limit comment in pull request about bundle size changes"
  width="686" height="289">
</p>

[Postman Collection]: https://we.tl/t-xpwNenND6k


