package com.github.halab4dev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringSentryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringSentryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.trace("Warning");
		log.debug("Debug");
		log.info("Info");
		log.warn("Warning");
		log.error("Error");
	}
}
