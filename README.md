# Java CRUD Rest API for Notes

### Framework
- Java SpringBoot

### Database
- MongoDB deployed using docker. To run 



    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'com.github.vladimir-bukhtoyarov:bucket4j-core:7.3.0'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'io.jsonwebtoken:jjwt-api:0.11.2'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.2',
    'io.jsonwebtoken:jjwt-jackson:0.11.2',
    'org.apache.commons:commons-lang3:3.0'
    implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.14.0'

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
	testImplementation "org.junit.jupiter:junit-jupiter:5.8.1"
	testImplementation 'org.testcontainers:junit-jupiter'
	testImplementation "org.testcontainers:mongodb:1.19.3"


Details explaining the choice of framework/db/ any 3rd party tools.
instructions on how to run your code and run the tests.
Any necessary setup files or scripts to run your code locally or in a test environment.
