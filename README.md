# Java CRUD Rest API for Notes
Simple CRUD operations using Java SpringBoot and MongoDB 

### Framework
- Java SpringBoot

### Database
- MongoDB deployed using docker

### Dependencies and tools
- Spring Web Security  (Jwt Authentication Filter for handling bearer token).
- JUnit and TestContainers for Integration tests.
- Bucket4j for rate limiting, set to 20 requests per minutes.

### Run
Create a .env file to pass the mongodb environment variables to the docker-compose.yml file
```agsl
MONGO_INITDB_DATABASE=admin
MONGO_INITDB_ROOT_USERNAME=<root db username>
MONGO_INITDB_ROOT_PASSWORD=<root db password>
MONGODB_DB=<db name>
MONGODB_USER=<db user>
MONGODB_PASSWORD=<db password>
```
Then run
```agsl
docker-compose --env-file .env up -d
```
Once the mongodb container is up, change the `application.yml` file to reflect the mongodb database name and credentials. Then run

```agsl
./gradlew bootRun 
```

### Requests

#### Signup
```agsl
curl --location 'http://localhost:8080/api/auth/signup' \
--header 'Content-Type: application/json' \
--data '{
    "firstName": "<insert firstName>",
    "lastName": "<insert lastName>",
    "username": "<insert username>",
    "password": "<insert password>"
}'
```
```agsl
// Response
{ "token": "<generated token>" }
```
#
#### Login
```agsl
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "<insert username>",
    "password": "<insert password>"
}'
```
```agsl
// Response
{ token": "<generated token>" }
```
#
#### Fetch Notes
```agsl
curl --location 'http://localhost:8080/api/notes' \
--header 'Authorization: Bearer <generated token>' \
```
```agsl
// Response
[
    { "id": "sample-id", "title": "sample-title", "content": "sample-content" }
]
```

#
#### Fetch Note by id
```agsl
curl --location 'http://localhost:8080/api/notes/sample-id' \
--header 'Authorization: Bearer <generated token>'
```
```agsl
// Response
{ "id": "sample-id", "title": "sample-title", "content": "sample-content" }
```

#
#### Create Note
```agsl
curl --location 'http://localhost:8080/api/notes' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <generated token>' \
--data '{
    "title": "<insert title>",
    "content": "<insert content>"
}'
```
```agsl
// Response
{ "id": "sample-id", "title": "sample-title", "content": "sample-content" }
```

#
#### Edit Note
```agsl
curl --location --request PUT 'http://localhost:8080/api/notes/sample-id' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <generated token>' \
--data '{
    "title": "<insert title>",
    "content": "<insert content>"
}'
```
```agsl
// Response
{ "id": "sample-id", "title": "sample-title", "content": "sample-content" }
```

#
#### Share Note
```agsl
curl --location 'http://localhost:8080/api/notes/6594e47236da1f23cee34d3d/share' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <generated token>' \
--data '{
    "username": "<username to share note to>"
}'
```
```agsl
// Response
Note has been shared
```

#
#### Search Note
```agsl
curl --location 'http://localhost:8080/api/search?query=<note title regex>' \
--header 'Authorization: Bearer <generated token>'
```
```agsl
// Response
[
    { "id": "sample-id", "title": "sample-title", "content": "sample-content" }
]
```
