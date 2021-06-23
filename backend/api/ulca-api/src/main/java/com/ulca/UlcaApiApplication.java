package com.ulca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UlcaApiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(UlcaApiApplication.class, args);
	}

}
