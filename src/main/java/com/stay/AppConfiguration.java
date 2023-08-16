package com.stay;

import com.stay.domain.entity.RoomEntity;
import com.stay.domain.model.CacheConfigModel;
import com.stay.lifecycle.ElectricityGenerator;
import com.stay.cache.BaseCache;
import com.stay.cache.BaseCacheImpl;
import com.stay.cache.CacheFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource(value = "classpath:config.properties")
// To import another Config Class : @Import(AppConfigFour.class)
// To import from a Config XML : @ImportResource(value="classpath:spring/app-context.xml")
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
                        new CacheConfigModel(getProperty("cache.config.default.timeToLive"),
                                getProperty("cache.config.default.timerInterval"),
                                getProperty("cache.config.default.maxItems"))));
        factory.addBean("room",
                new BaseCacheImpl<Integer, RoomEntity>(
                        new CacheConfigModel(getProperty("cache.config.room.timeToLive"),
                                getProperty("cache.config.room.timerInterval"),
                               getProperty("cache.config.room.maxItems"))));
        return factory;
    }

    @Bean("room-cache")
    // @DependsOn("cacheFactoryBean")
    public BaseCache roomCacheBean() {
        return (BaseCache) cacheFactoryBean().getBean("room", RoomEntity.class);
    }

    @Lazy
    @Bean(initMethod = "generatorStarted", destroyMethod = "stopGenerator")
    public ElectricityGenerator electricityGen() {
        return new ElectricityGenerator();
        // If initialization fails the exception wraps into BeanCreationException
    }

    private String getProperty(String propertyPath) {
        return Objects.requireNonNull(env.getProperty(propertyPath),
                "env: " + propertyPath + " not provided!");
    }

    /*@Bean(destroyMethod = "stop")
    public MongodProcess mongodProcess() throws IOException {
        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(newNet(MONGO_DB_URL, MONGO_DB_PORT, Network.localhostIsIPv6()))
                .build();
        MongodExecutable mongodExecutable = MongodStarter.getDefaultInstance().prepare(mongodConfig);
        return mongodExecutable.start();
    }*/

	/*@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}*/

	/*@Bean("messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource =
				new ResourceBundleMessageSource();
		messageSource.setBasenames("language/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}*/
}
