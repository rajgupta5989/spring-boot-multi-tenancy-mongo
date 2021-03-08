package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sample")
public class SpringBootMultiTenancyMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultiTenancyMongoApplication.class, args);
	}

}
