# Health Care API

* Java 17
* Spring Boot 3.1.5
* Dependency Management: Maven
* Database: H2
* Unit Tests: JUnit 5 & Mockito
* Inspection of code quality: Sonarqube & JaCoCo
* Swagger
* API Tests: Postman

### How to Build :hammer:

```shell
mvn clean install -DskipTests
```

### How to up :whale2:

```shell
docker-compose -f docker-compose.yml up -d
```

### How run code coverage :detective:

```shell
mvn clean install sonar:sonar -Pcoverage
```

> PS: to run this the sonarqube on docker must be running, and maybe be needed to update sonar.password property on pom.xml

### Useful links:

* :bucket: [H2 Console](http://localhost:8080/h2) (*user: sa - password: sa*)
* :green_book: [Swagger](http://localhost:8080/swagger-ui/index.html)
* :bulb: [Sonarqube](http://localhost:9000/dashboard?id=com.github.gaboso%3Ahealth-care-api) (*needs docker | user: admin - default password: admin*)
* :cop: [Actuator](http://localhost:8080/management)
* :rocket: [Postman Collection](postman/MedicalRecords.postman_collection.json) (*This collection needs to be imported on Postman to be used*)