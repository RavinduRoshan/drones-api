# DronesAPI

This is a RESTful API for managing drones and their related services.

## Installation
- Clone the repository: git clone https://github.com/RavinduRoshan/drones-api
- Import the project into your IDE
- Java 11 is required to run this project
- Run the project using the mvn spring-boot:run command or directly from your IDE
- Database and the pre-requisist data will be generated and stored in a in-memory H2 database when you start the application
- #### To access the database locally
    - Navigate to http://localhost:8080/api/h2-console once you start the application
    - Set JDBC URL as **jdbc:h2:mem:testdb**
    - Set Username as **sa**
    - Password is not required
## Usage
This API provides the following endpoints:

#### Register a drone
Endpoint: *POST /drone/register*

This endpoint is used to register a new drone.

*Example request body:*
```
{
    "serialNumber": "DR07-NIKON_78",
    "model": "Lightweight",
    "weight": 175,
    "batteryCapacity": 0.95,
    "state": "IDLE"
}
```

#### Load medications to a drone
Endpoint: PUT /drone/{serialNumber}/load

This endpoint is used to load medications to a drone.

*Example request body:*
```
{
"medications": [
        {
            "code": "MED_170",
            "name": "ASDF",
            "weight": 150.5,
            "image": "/resources/images/MED_170.png"
        },
        {
            "code": "MED_171",
            "name": "ERFG",
            "weight": 100.5,
            "image": "/resources/images/MED_171.png"
        }
    ]
}
```
#### Get loaded medications of a drone
Endpoint: *GET /drone/{serialNumber}/medications*

This endpoint is used to fetch the medications loaded in a drone.

#### Get available drones for loading
Endpoint: *GET /drone/availableForLoading*

This endpoint is used to fetch the available drones that can be loaded.

#### Check battery level of a drone
Endpoint: *GET /drone/{serialNumber}/checkBattery*

This endpoint is used to check the battery level of a drone.

#### Fetch all registered drones
Endpoint: *GET /drone*

This endpoint is used to fetch all the registered drones.


## Response format
The API returns JSON response for all endpoints. Successful responses have a 200 status code. In case of errors, appropriate HTTP status codes and error messages are returned.

## Dependencies
This project uses the following dependencies:

- Spring Boot
- Logback
- Jackson-databind
- JUnit
- Mockito

## Authors
- Ravindu Liyanagamage (ravinduroshan.rr@gmail.com)