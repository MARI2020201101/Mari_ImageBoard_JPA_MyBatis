package com.mariworld.imageboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootImageboardJpaMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootImageboardJpaMybatisApplication.class, args);
	}

}
