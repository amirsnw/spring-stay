package com.stay;

import com.stay.domain.Room;
import com.stay.propertyEditor.cache.CacheConfigModel;
import com.stay.resource.cache.BaseCache;
import com.stay.resource.cache.BaseCacheImpl;
import com.stay.resource.cache.CacheFactory;
import com.stay.util.RoomValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public BaseCache roomCacheBean() throws Exception {
        return (BaseCache) cacheFactoryBean().getBean("cache:room", Room.class);
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
}
