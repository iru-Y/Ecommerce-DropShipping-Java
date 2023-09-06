package com.delivery.trizi.trizi;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.ComponentScan;

@Log4j2
@SpringBootApplication
@ComponentScan(basePackages = "com.delivery.trizi.trizi")
public class TriziApplication {
	public static void main(String[] args) {
			SpringApplication.run(TriziApplication.class, args);
			log.info("Subiu");
}
}
