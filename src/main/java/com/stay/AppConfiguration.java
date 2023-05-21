package com.stay;

import com.stay.domain.entity.RoomEntity;
import com.stay.lifecycle.ElectricityGenerator;
import com.stay.domain.model.CacheConfigModel;
import com.stay.resource.cache.BaseCache;
import com.stay.resource.cache.BaseCacheImpl;
import com.stay.resource.cache.CacheFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource(value = "classpath:config.properties")
// @Import(AppConfigFour.class) // To import another Config Class
// @ImportResource(value="classpath:spring/app-context.xml") // To import from Config XML
public class AppConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    Environment env;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CacheFactory cacheFactoryBean() {
        CacheFactory factory = new CacheFactory();
        factory.addBean("default",
                new BaseCacheImpl<String, Object>(
                        new CacheConfigModel(Long.parseLong(getProperty("cache.config.default.timeToLive")),
                                Long.parseLong(getProperty("cache.config.default.timerInterval")),
                                Integer.parseInt(getProperty("cache.config.default.maxItems")))));
        factory.addBean("room",
                new BaseCacheImpl<Integer, RoomEntity>(
                        new CacheConfigModel(Long.parseLong(getProperty("cache.config.room.timeToLive")),
                                Long.parseLong(getProperty("cache.config.room.timerInterval")),
                                Integer.parseInt(getProperty("cache.config.room.maxItems")))));
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
    // @DependsOn("cacheFactoryBean")
    public BaseCache roomCacheBean() {
        return (BaseCache) cacheFactoryBean().getBean("room", RoomEntity.class);
    }

    @Bean(initMethod = "generatorStarted", destroyMethod = "stopGenerator")
    @Lazy
    // Default bootstrapping is eager by ApplicationContext
    public ElectricityGenerator electricityGen() {
        return new ElectricityGenerator();
        // If initialization fails the exception wrap into BeanCreationException
    }

    private String getProperty(String propertyPath) {
        return Objects.requireNonNull(env.getProperty(propertyPath),
                "env: " + propertyPath + " not provided!");
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

    // Note: We can use default-init-method="init" in xml to have the same method for initializing hook
}
