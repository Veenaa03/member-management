# Surest Member Management

A Spring Boot application for managing members with JWT authentication, role-based access, pagination, caching, and PostgreSQL database.

---

## 🚀 Features
- Member CRUD (Admin only)
- Member read with pagination, filtering, caching
- JWT Authentication (Login endpoint)
- Role-based security (ADMIN / USER)
- Flyway DB migrations
- Integration & unit tests with JUnit + Mockito
- Gradle build
- Docker + PostgreSQL support

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security 6
- PostgreSQL
- Flyway
- Spring Data JPA
- JUnit / Mockito
- Docker Compose

---

Project Setup:
• Used Spring Boot as the framework.
• Used PostgreSQL as the database.
• Used Gradle as the build tool.
• Configured Spring Data JPA for database access.
• Used Flyway for database migration.
• Used Spring Security with JWT for authentication.

Testing:
• Written unit tests using JUnit 5.
• Used Mockito for mocking dependencies.
• Generated a JaCoCo coverage report.
• Ensured >80% code coverage across service  and controller  layers.

Additional:
• Added Global exception Handler with meaningful messages
• Added custom Business exceptions with meaningful messages.


## Test Coverage Report
<img src="images/test_coverage_report.png" alt="Coverage Report" width="100%">

---

## Build Screenshots
<img src="images/build_success.png" alt="Build Success" width="100%">
<img src="images/build_up.png" alt="Build Up" width="100%">

---

## Postman Screenshots

### Token Generation
<img src="images/postman_tokengeneration.png" alt="Token Generation" width="100%">

### Create Member (POST)
<img src="images/postman_post_member.png" alt="Post Member" width="100%">

### Get Member by ID
<img src="images/postman_getmember_byId.png" alt="Get Member by ID" width="100%">

### Get Members
<img src="images/postman_get_member.png" alt="Get Members" width="100%">

### Update Member
<img src="images/postman_update_member.png" alt="Update Member" width="100%">

### Delete Member
<img src="images/postman_delete_member.png" alt="Delete Member" width="100%">
