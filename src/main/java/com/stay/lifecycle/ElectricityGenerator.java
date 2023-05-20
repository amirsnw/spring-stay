package com.stay.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


// After calling constructor, BeanPostProcessor checks for
public class ElectricityGenerator implements SmartLifecycle,
                                    BeanNameAware, // instead of Nameable interface
                                    BeanClassLoaderAware,
                                    ApplicationContextAware,
                                    InitializingBean,
                                    DisposableBean
        // Interface Hooks are not decouple
        // Used when we have multiple bean with the same TYPE
{

    // SmartLifecycle Methods
    private boolean isRunning = false;

    public ElectricityGenerator() {
        System.out.println("(0) Constructor (then beans get analyzed by BeanPostProcessor for any lifecycle method");
    }

    @Override
    public void start() {
        // Spring context is started
        System.out.println("(7) Generator Started (start SmartLifecycle interface)");
        isRunning = true;
    }

    @Override
    public void stop() {
        // Add your shut-down logic here
        System.out.println("Generator Started (stop SmartLifecycle interface)");
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        System.out.println("Generator Started (isRunning SmartLifecycle interface)");
        return isRunning;
    }

    @Override
    public int getPhase() {
        // Return the phase at which this bean should be started and stopped
        System.out.println("Generator Started (getPhase SmartLifecycle interface)");
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        // Return true if this bean should be started automatically
        System.out.println("Generator Started (isAutoStartup SmartLifecycle interface)");
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        // Add your shut-down logic here and call the callback when complete
        isRunning = false;
        System.out.println("Generator Started (stop SmartLifecycle interface)");
        callback.run();
    }

    // BeanNameAware Lifecycle Method
    @Override
    public void setBeanName(String s) {
        // Runs before initialization and destroy
        // Before returning the first instance by ApplicationContext.getBean()
        System.out.println("(1) Generator Started (BeanNameAware interface)");
        // We can change Bean name by our logic
    }

    // BeanClassLoader Lifecycle Method
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("(2) Generator Started (BeanClassLoader interface)");
    }

    // ApplicationContext Lifecycle Method
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBean("room-cache"); // Inject Programmatically
        System.out.println("(3) Generator Started (ApplicationContext interface)");
        /*if (applicationContext instanceof GenericApplicationContext) {
            ((GenericApplicationContext) applicationContext).registerShutdownHook();
        }*/
        // We can access the bean that has injected this class into and
        // use programmatic injection instead of lookup injection (not recommended)
    }

    // Annotation-based Post Construct Hook
    // Decoupled
    @PostConstruct
    // Analyzed by CommonAnnotationBeanPostProcessor
    public void init() {
        // Add your initialization logic here
        // First method after constructor
        // Found by CommonAnnotationBeanPostProcessor spring bean
        // Then dependencies will be injected by BeanFactory and then Calling BeanFactoryAware and ApplicationContextAware
        System.out.println("(4) Generator Started (@PostConstruct");
    }

    // InitializingBean Lifecycle Method
    @Override
    public void afterPropertiesSet() {
        // After properties are set
        System.out.println("(5) Generator Started (InitializingBean interface)");
    }

    // Called by bean annotation attribute (initMethod)
    // Needs no argument
    // private for safety
    private void generatorStarted() {
        System.out.println("(6) Generator Started (initMethod)");
        // BeanCreationException  may happen, so you return null for an object than throwing exception
    }

    // Annotation-based Pre Destroy Hook
    // Decoupled
    @PreDestroy
    public void cleanup() {
        // Add your cleanup logic here
        // First method on destroy
        System.out.println("(1) Generator cleanup (@PreDestroy)");
    }

    // DisposableBean Lifecycle Method
    @Override
    public void destroy() throws Exception {
        System.out.println("(2) Generator cleanup (DisposableBean interface)");
    }

    // Called by bean annotation attribute (destroyMethod)
    public void stopGenerator() {
        System.out.println("(3) Generator cleanup (destroyMethod)");
    }
}
