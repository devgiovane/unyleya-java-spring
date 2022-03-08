# Webserver Spring with Java

### About


### Commands

```bash
docker-compose up -d

./gradlew bootRun
```

or

```bash
docker-compose up -d

./gradlew build
java -jar ./build/libs/api-0.0.1-SNAPSHOT.jar
```

### Restfull Methods

```
GET - http://localhost:8080/api/v1/user
GET - http://localhost:8080/api/v1/user/1
POST - http://localhost:8080/api/v1/user
PATCH - http://localhost:8080/api/v1/user/1
PUT - http://localhost:8080/api/v1/user/1
DELETE - http://localhost:8080/api/v1/user/1
```
