package com.github.halab4dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringPrometheusGrafanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPrometheusGrafanaApplication.class, args);
	}
}
