package com.stay.resource.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheFactory implements BeanNameAware,
        ApplicationContextAware, FactoryBean<BaseCache>, BeanFactory {

    private final Map<String, BaseCache> beans = new HashMap<>();
    private String beanName;
    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public BaseCache getObject() throws Exception {
        return beans.get("default");
        // return new BaseCacheImpl<Integer, Room>(120, 500, 6);
    }

    @Override
    public Class<?> getObjectType() {
        return BaseCache.class;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBean(name, (Class<?>) null);
    }

    @Override
    public <T> T getBean(String name, @Nullable Class<T> requiredType) throws BeansException {
        if (name.startsWith("cache:")) {
            String qualifier = name.substring("cache:".length());
            BaseCache bean = beans.get(qualifier);
            if (bean == null) {
                throw new NoSuchBeanDefinitionException(BaseCache.class, "No bean found with qualifier: " + qualifier);
            }
            return (T) bean;
        }
        return null;
    }

    public void addBean(String qualifier, BaseCache bean) {
        beans.put(qualifier, bean);
    }

    /*********************************************************************/
    @Override
    public Object getBean(String s, Object... objects) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> aClass) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> aClass, Object... objects) throws BeansException {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType resolvableType) {
        return null;
    }

    @Override
    public boolean containsBean(String s) {
        return false;
    }

    @Override
    public boolean isSingleton(String s) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isPrototype(String s) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String s, ResolvableType resolvableType) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String s, Class<?> aClass) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public Class<?> getType(String qualifier) throws NoSuchBeanDefinitionException {
        return beans.get(qualifier).getClass();
    }

    @Override
    public Class<?> getType(String qualifier, boolean b) throws NoSuchBeanDefinitionException {
        return beans.get(qualifier).getClass();
    }

    @Override
    public String[] getAliases(String s) {
        String aliases[] = new String[beans.size()];
        return beans.keySet().toArray(aliases);
    }
}
