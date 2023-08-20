package com.delivery.trizi.trizi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TriziApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriziApplication.class, args);
	}

}
