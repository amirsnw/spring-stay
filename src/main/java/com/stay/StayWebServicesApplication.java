package com.stay;

import com.stay.resource.cache.CacheFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StayWebServicesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StayWebServicesApplication.class, args);
		Runtime.getRuntime().addShutdownHook(new Thread(context::close)); // It also flushes persistent context
	}
}
