package com.stay;

import com.stay.domain.entity.RoomEntity;
import com.stay.lifecycle.ElectricityGenerator;
import com.stay.domain.model.CacheConfigModel;
import com.stay.resource.cache.BaseCache;
import com.stay.resource.cache.BaseCacheImpl;
import com.stay.resource.cache.CacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfiguration {

    /*@Value("120-500-6")
    public CacheConfigModel cacheConfigModel;*/

    @Bean
    public CacheFactory cacheFactoryBean() {
        CacheFactory factory = new CacheFactory();
        factory.addBean("default",
                new BaseCacheImpl<String, Object>(new CacheConfigModel(60, 200, 3)));
        factory.addBean("room",
                new BaseCacheImpl<Integer, RoomEntity>(new CacheConfigModel(120, 500, 6)));
        return factory;
    }
    // Note: Use FactoryBean to inject third-party classes that should be initialized by their specific getInstance method
    /*
        XML solution for to get the default Cache bean:

        <!-- This factory contains calling the getInstance method of the thirdParty class -->
        <bean id="defaultCacheFactoryBean"
             class="com.stay.resource.cache.CacheFactory"
             p:beanName="default"/>

        <!-- Third-party bean with no access to its code -->
        <bean id="default-cache" factory-bean="defaultCacheFactoryBean" factory-method="getObject"/>

        <!-- Modify previously scanned bean definition -->
        <bean id="reservationController"
            class="com.stay.controller.ReservationController">
            <property name="cacheService" ref="default-cache" />
        </bean>
    */

    @Bean("room-cache")
    public BaseCache roomCacheBean() {
        return (BaseCache) cacheFactoryBean().getBean("room", RoomEntity.class);
    }


    @Bean(initMethod = "generatorStarted", destroyMethod = "stopGenerator")
    @Lazy
    // Default eager bootstrapping is by ApplicationContext
    public ElectricityGenerator electricityGen() {
        return new ElectricityGenerator();
        // If initialization fails the exception wrap into BeanCreationException
    }

    /*@Bean
	public CacheFactory roomCacheResolver() {
		return new CacheFactory(120, 500, 6);
	}*/

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

    // Note: We can use default-init-method="init" in xml to have the same method for initializing hook
}
