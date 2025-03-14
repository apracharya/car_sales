package com.apr.car_sales;

import io.github.cdimascio.dotenv.Dotenv;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;


//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class CarSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSalesApplication.class, args);
		System.out.println("Started! ");
	}

}
