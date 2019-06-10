package com.tdd.gengulaytdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GengulayTddApplication {

	public static void main(String[] args) {
		SpringApplication.run(GengulayTddApplication.class, args);
	}

}
