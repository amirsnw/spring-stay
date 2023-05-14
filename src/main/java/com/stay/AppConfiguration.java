package com.stay;

import com.stay.domain.Room;
import com.stay.lifecycle.ElectricityGenerator;
import com.stay.propertyEditor.cache.CacheConfigModel;
import com.stay.resource.cache.BaseCache;
import com.stay.resource.cache.BaseCacheImpl;
import com.stay.resource.cache.CacheFactory;
import com.stay.util.RoomValidator;
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
        factory.addBean("default", null);
        factory.addBean("room",
                new BaseCacheImpl<Integer, Room>(new CacheConfigModel(120, 500, 6)));
        return factory;
    }

    @Bean("room-cache")
    public BaseCache roomCacheBean() {
        return (BaseCache) cacheFactoryBean().getBean("cache:room", Room.class);
    }

    @Bean(initMethod = "generatorStarted", destroyMethod = "stopGenerator")
    @Lazy
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
