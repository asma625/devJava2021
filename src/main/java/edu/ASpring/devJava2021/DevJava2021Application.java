package edu.ASpring.devJava2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevJava2021Application {

	public static void main(String[] args) {
		SpringApplication.run(DevJava2021Application.class, args);
	}

}
