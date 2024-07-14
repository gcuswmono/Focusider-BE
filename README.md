## Authentication Service

### Overview

This is a robust authentication service built with Spring Boot 3.3.1. It provides secure user registration, login, and token refresh functionalities using JWT (JSON Web Tokens).

### Features

- User registration (signup)
- User authentication (login)
- Token-based authentication using JWT
- Token refresh mechanism
- Secure password hashing
- CORS configuration
- Swagger API documentation

### Technologies

- Java 21 (OpenJDK)
- Spring Boot 3.3.1
- Spring Security
- Spring Data JPA
- MariaDB
- Redis
- Gradle
- JWT (JSON Web Tokens)
- Swagger (OpenAPI)

### Project Structure

The project follows a clean architecture with clear separation of concerns:

```
src/
├── main/
│   ├── java/
│   │   └── mono/
│   │       └── focusider/
│   │           ├── application/
│   │           │   ├── dto/
│   │           │   └── service/
│   │           ├── domain/
│   │           │   ├── model/
│   │           │   └── repository/
│   │           ├── infrastructure/
│   │           │   ├── config/
│   │           │   ├── exception/
│   │           │   └── security/
│   │           └── presentation/
│   │               └── controller/
│   └── resources/
│       └── application.yaml
└── test/
    └── java/
```

### Setup and Installation

1. Ensure you have Java 21 and Gradle installed on your system.
2. Clone the repository: `git clone [repository-url]`
3. Navigate to the project directory: `cd [project-directory]`
4. Build the project: `./gradlew build`
5. Run the application: `./gradlew bootRun`

### Configuration

The main configuration file is `src/main/resources/application.yaml`. You may need to adjust database credentials and other environment-specific settings.

### API Endpoints

- POST `/api/auth/signup`: Register a new user
- POST `/api/auth/login`: Authenticate a user and receive JWT tokens
- POST `/api/auth/refresh`: Refresh the access token using a valid refresh token

For detailed API documentation, run the application and visit `http://localhost:8080/swagger-ui.html`

### Security

- Passwords are securely hashed using BCrypt
- JWTs are used for stateless authentication
- Refresh tokens are stored in Redis for added security
- CORS is configured to allow requests from trusted origins