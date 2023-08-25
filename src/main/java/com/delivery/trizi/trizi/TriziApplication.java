package com.delivery.trizi.trizi;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextException;

@Log4j2
@SpringBootApplication
public class TriziApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(TriziApplication.class, args);
			log.info("Subiu");
		} catch (ApplicationContextException e){
			log.debug("deu foi ruim");
	}

}
}
