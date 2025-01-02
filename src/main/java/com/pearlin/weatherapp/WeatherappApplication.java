package com.pearlin.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherappApplication {

	public static void main(String[] args) {
              System.out.println("Starting Spring Boot application...");
		SpringApplication.run(WeatherappApplication.class, args);
	}

}
