package com.openclassroom.SafetyNet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.openclassroom.SafetyNet")
public class SafetyNetApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("////////// Click to access the app : http://localhost:8080/swagger-ui/index.html  ///////////");

	}
}
