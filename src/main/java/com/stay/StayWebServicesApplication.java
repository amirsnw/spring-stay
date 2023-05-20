package com.stay;

import com.stay.resource.cache.CacheFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

@SpringBootApplication
public class StayWebServicesApplication {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		// ConfigurableApplicationContext is one implementation of beanFactory
		context = SpringApplication.run(StayWebServicesApplication.class, args);

		Runtime.getRuntime().addShutdownHook(new Thread(context::close));
		// Calling explicitly ConfigurableBeanFactory.destroySingletons()
		// Clear all resources used by beans
		// Flush memory and persistent context
	}

	private static void scanPackage() {
		ClassPathBeanDefinitionScanner scanner = getClassPathBeanDefinitionScanner();
		scanner.scan("com.example.my.package");
	}

	private static void showAllBeans() {
		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println("Bean Name: " + beanName);
		}
	}

	private static ClassPathBeanDefinitionScanner getClassPathBeanDefinitionScanner() {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		return new ClassPathBeanDefinitionScanner(beanFactory);
	}
}
