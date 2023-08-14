package com.stay.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("rawtypes")
public class CacheFactory implements FactoryBean<BaseCache>, BeanFactory {

    private final Map<String, BaseCache> beans = new HashMap<>();

    private final String defaultBeanName = "default";

    public void addBean(String qualifier, BaseCache bean) {
        beans.put(qualifier, bean);
    }

    /**
     * ************************FactoryBean Methods**************************
     * **/
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<BaseCache> getObjectType() {
        return BaseCache.class;
    }

    @Override
    public BaseCache getObject() {
        return (BaseCache) getBean(this.defaultBeanName);
    }

    /**
     * ************************BeanFactory Methods**************************
     * **/
    @Override
    public Object getBean(String name) throws BeansException {
        return getBean(name, (Class<?>) null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, @Nullable Class<T> requiredType) throws BeansException {
        String qualifier = name.length() > 0 ? name : defaultBeanName;
        BaseCache<?, T> bean = beans.get(qualifier);
        if (bean == null) {
            throw new NoSuchBeanDefinitionException(BaseCache.class,
                    "No bean found with qualifier: " + qualifier);
        }
        return (T) bean;
    }

    @Override
    public Object getBean(String name, Object... objects) throws BeansException {
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
    // Check whether managing and creating a singleton bean or not
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
        String[] aliases = new String[beans.size()];
        return beans.keySet().toArray(aliases);
    }
}
