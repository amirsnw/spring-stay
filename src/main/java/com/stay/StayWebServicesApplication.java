package com.stay;

import com.stay.resource.cache.RoomCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StayWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayWebServicesApplication.class, args);
	}

	@Bean
	public RoomCache roomCacheResolver() {
		return new RoomCache(120, 500, 6);
	}

//	@Bean
//	public LocaleResolver localeResolver() {
//		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//		localeResolver.setDefaultLocale(Locale.US);
//		return localeResolver;
//	}

//	@Bean("messageSource")
//	public MessageSource messageSource() {
//		ResourceBundleMessageSource messageSource =
//				new ResourceBundleMessageSource();
//		messageSource.setBasenames("language/messages");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
}
