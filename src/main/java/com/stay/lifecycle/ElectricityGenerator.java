package com.stay.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


// After calling constructor, BeanPostProcessor checks for
public class ElectricityGenerator implements SmartLifecycle,
                                    BeanNameAware,
                                    BeanClassLoaderAware,
                                    ApplicationContextAware,
                                    InitializingBean,
                                    DisposableBean
        // Used when we have multiple bean with the same TYPE
{

    // SmartLifecycle Methods
    private boolean isRunning = false;

    public ElectricityGenerator() {
        System.out.println("(0) Generator Constructor (then beans get analyzed by BeanPostProcessor for any lifecycle method");
    }

    /**
     * ************************SmartLifecycle Methods**************************
     * **/
    @Override
    public void start() {
        // Spring context is started
        System.out.println("SmartLifecycle:start()");
        isRunning = true;
    }

    @Override
    public void stop() {
        // Add your shut-down logic here
        System.out.println("SmartLifecycle:stop()");
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        System.out.println("SmartLifecycle:isRunning()");
        return isRunning;
    }

    @Override
    public int getPhase() {
        // Return the phase at which this bean should be started and stopped
        System.out.println("SmartLifecycle:getPhase()");
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        // Return true if this bean should be started automatically
        System.out.println("SmartLifecycle:isAutoStartup()");
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        // Add your shut-down logic here and call the callback when complete
        isRunning = false;
        System.out.println("SmartLifecycle:stop(Runnable)");
        callback.run();
    }

    /**
     * ************************BeanNameAware Method**************************
     * **/
    @Override
    public void setBeanName(String s) {
        // Runs before initialization and destroy
        // Before returning the first instance by ApplicationContext.getBean()
        System.out.println("(1) BeanNameAware:setBeanName(String)");
        // We can change Bean name by our logic
    }

    /**
     * ************************BeanClassLoader Method**************************
     * **/
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("(2) BeanClassLoader:setBeanClassLoader(ClassLoader)");
    }

    /**
     * ************************ApplicationContext Method**************************
     * **/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBean("room-cache"); // Inject Programmatically
        System.out.println("(3) ApplicationContext:setApplicationContext(ApplicationContext)");
        /*if (applicationContext instanceof GenericApplicationContext) {
            ((GenericApplicationContext) applicationContext).registerShutdownHook();
        }*/
    }

    // Annotation-based Post Construct Hook
    @PostConstruct
    public void init() {
        // First method after constructor
        // Then dependencies will be injected by BeanFactory and then Calling BeanFactoryAware and ApplicationContextAware
        System.out.println("(4) @PostConstruct");
    }

    /**
     * ************************InitializingBean Method**************************
     * **/
    @Override
    public void afterPropertiesSet() {
        // After properties are set
        System.out.println("(5) InitializingBean:afterPropertiesSet()");
    }

    /**
     * ************************DisposableBean methods**************************
     * **/
    @Override
    public void destroy() {
        System.out.println("(2) Generator cleanup (DisposableBean interface)");
    }

    /**
     * ************************Annotation-Based Hooks**************************
     * **/
    @PreDestroy
    public void cleanup() {
        // First method on destroy
        System.out.println("(1) @PreDestroy:cleanup()");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        System.out.println("(?) @EventListener:initialize()");
    }

    // Called by bean annotation attribute (initMethod) : AppConfiguration.java or config.xml
    private void generatorStarted() {
        System.out.println("(6) Generator Started (initMethod)");
    }

    // Called by bean annotation attribute (destroyMethod) : AppConfiguration.java or config.xml
    public void stopGenerator() {
        System.out.println("(3) Generator cleanup (destroyMethod)");
    }


}
