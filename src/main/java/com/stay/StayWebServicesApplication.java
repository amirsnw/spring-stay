package com.stay;

import com.stay.resource.cache.CacheFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StayWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayWebServicesApplication.class, args);
	}
}
