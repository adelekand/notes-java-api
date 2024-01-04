package com.adelekand.speer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.adelekand.speer.repository")
public class SpeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeerApplication.class, args);
	}

}
