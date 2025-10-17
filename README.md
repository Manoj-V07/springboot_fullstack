# Train Booking System (springboot_fullstack)

A simple Spring Boot full-stack example application for managing trains, users and ticket bookings.

This repository demonstrates a small CRUD web app built with Spring Boot, Spring Data JPA, Thymeleaf templates and Bootstrap for the front-end.

## Tech stack

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA (Hibernate)
- Thymeleaf (server-side templates)
- Bootstrap 5 (via CDN)
- MySQL (runtime JDBC connector)
- Lombok (for entity boilerplate reduction)
- Maven (build)

## Main features implemented

- Manage Trains: create, read, update, delete trains (name, source, destination, base price, discount).
- Manage Users: create, read, update, delete users (name, email).
- Book Tickets: create and manage tickets that link a User and a Train. Ticket creation stores booking timestamp and final price (base price minus discount percentage).
- Server-rendered UI: Thymeleaf templates for list and form pages for trains, users and tickets. A new `home.html` provides a navbar and hero area with quick links.

## Project structure (important folders)

- `src/main/java` - application sources
	- `com.example.trainbookingsystem.controller` - Spring MVC controllers (TrainController, TicketController, UserController, HomeController)
	- `com.example.trainbookingsystem.entity` - JPA entities (Train, User, Ticket)
	- `com.example.trainbookingsystem.repository` - Spring Data JPA repositories
	- `com.example.trainbookingsystem.service` - application business logic
- `src/main/resources/templates` - Thymeleaf HTML templates (organized by `trains/`, `users/`, `tickets/` and `home.html`)
- `src/main/resources/static` - static frontend assets (CSS/JS)
- `pom.xml` - Maven project and dependencies

## Configuration

Primary configuration lives in `src/main/resources/application.properties`.

Important properties to check before running:

- `spring.datasource.url` - JDBC URL to your MySQL database
- `spring.datasource.username` / `spring.datasource.password` - DB credentials
- `spring.jpa.hibernate.ddl-auto` - currently set to `update` (creates/updates schema automatically)

The sample `application.properties` included is configured for a local MySQL instance (`railwaydb`). Update these values for your environment.

## How to run (Windows cmd.exe)

1. Build the project (skip tests for faster feedback):

```cmd
cd "c:\Users\MANOJ V\springboot_fullstack\train-booking-system\train-booking-system"
mvnw.cmd package -DskipTests
```

2. Run the application:

```cmd
mvnw.cmd spring-boot:run
```

3. Open the app in your browser at:

```
http://localhost:8080/
```

## Database notes

- The app expects a MySQL database. If you don't have one, create `railwaydb` (or change the JDBC URL in `application.properties`).
- Current `spring.jpa.hibernate.ddl-auto=update` will create tables automatically based on entities but won't remove columns.

## Tests

There are basic unit tests under `src/test/java` for service classes. Run them with:

```cmd
mvnw.cmd test
```

## UI

- Frontend uses server-rendered Thymeleaf templates and Bootstrap CSS from CDN.
- The `home.html` includes a navbar and a hero image with links to `Trains`, `Tickets`, and `Users` sections.

## Potential improvements / next steps

- Add authentication/authorization (Spring Security).
- Add client-side validation and richer UI (e.g., single-page components, Vue/React) if required.
- Add integration tests with an in-memory DB (H2) for CI.
- Add input sanitization and stronger error handling.
- Introduce DTOs and request validation for safer API boundaries.

## Security note

The repository's `application.properties` currently contains a password. Do not commit real credentials in public repositories. Consider using environment variables or Spring Boot externalized configuration for production.

---

If you want, I can:

- Run the project build now and fix any errors.
- Swap the hero image to a local static asset and add `static/images/`.
- Convert templates to a common layout (Thymeleaf fragments) so header/footer are shared.
