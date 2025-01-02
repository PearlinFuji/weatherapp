# WeatherApp

WeatherApp is a Spring Boot-based Java application that provides weather information using the OpenWeather API and stores historical weather data in a database.

## Features

- Fetch current weather information by postal code.
- Validate postal codes for accuracy.
- Save weather history to a database for future retrieval.
- Retrieve weather history by user or postal code.

## Technologies Used

- **Java**: Core language for the application.
- **Spring Boot**: Framework for application development.
- **OpenWeather API**: Source for weather information.
- **MySQL**: Database for storing weather history.
- **Maven**: Dependency management.
- **JUnit & Mockito**: For unit testing.

## Prerequisites

- Java 17 or later
- Maven
- MySQL
- OpenWeather API Key

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/PearlinFuji/WeatherApp.git
cd WeatherApp
```

### Configure the Application

1. Create a MySQL database:
   ```sql
   CREATE DATABASE weatherapp;
   ```

2. Update the `application.properties` file in `src/main/resources/` with your database and API configurations:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/weatherapp
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password

   weather.api.key=your_openweather_api_key
   weather.api.url=https://api.openweathermap.org/data/2.5/weather
   ```

### Build and Run the Application

1. Build the application:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   Deploy the WAR file to your application server (e.g., Apache Tomcat, JBoss, etc.).
   ```

The application will be available at `http://localhost:8080/weather`.

## Endpoints

### Fetch Weather Information
- **URL**: `/fetch`
- **Method**: `GET`
- **Parameters**:
  - `postalCode`: The postal code to fetch weather for.
  - `user`: The name of the user.
- **Example**:
  ```http
  GET /fetch?postalCode=90210&user=JohnDoe
  ```

### Retrieve Weather History
- **URL**: `/history`
- **Method**: `GET`
- **Parameters**:
  - `postalCode` (optional): Filter by postal code.
  - `user` (optional): Filter by user.
- **Example**:
  ```http
  GET /history?postalCode=90210&user=JohnDoe
  ```

## Testing

Run unit tests using:

```bash
mvn test
```
## Swagger Integration

The application includes Swagger for API documentation and testing. Once the application is running, you can access the Swagger UI at: `http://localhost:8080/weather/swagger-ui.html`

## Directory Structure

```
WeatherApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/pearlin/weatherapp/
│   │   │       ├── controller/       # Controller layer
│   │   │       ├── service/          # Service layer
│   │   │       ├── dao/              # Data Access layer
│   │   │       ├── domain/           # JPA Entities
│   │   │       ├── utils/            # Utility classes
│   │   │       ├── response/         # Custom Response layer
│   │   │       ├── swagger/          # Swagger Integration layer
│   │   └── resources/
│   │       ├── application.properties       # Configuration file
│   │       ├── application-dev.properties   # Configuration file for Dev environment
│   │       ├── application-prod.properties  # Configuration file for Prod environment
│   └── test/                          # Unit tests
├── pom.xml                            # Maven configuration
└── README.md                          # Project documentation
```

## Acknowledgments

- OpenWeather for providing the weather data API.
- The Spring Boot community for excellent resources and support.

