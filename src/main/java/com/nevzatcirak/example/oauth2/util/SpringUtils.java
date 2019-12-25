package com.nevzatcirak.example.oauth2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */

@SuppressWarnings("unchecked")
public class SpringUtils {

    private static final Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static <T> Map<String,T> getBeansOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return getApplicationContext().getBeansWithAnnotation(annotationType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getRootApplicationContext(ApplicationContext applicationContext) {
        if(applicationContext != null) {
            return applicationContext.getParent() == null ? applicationContext : getRootApplicationContext(applicationContext.getParent());
        }
        return null;
    }

    /**
     * 手动创建一个Bean
     * @param beanName
     * @param beanClass
     * @param beanProperties
     * @param singleton
     * @param initMethod
     * @param destroyMethod
     * @param handleAutowireDependency	 - 当前被创建的bean被其他已经存在的bean依赖需要autowire?
     * @return 如果return null则表示该名称的bean已经存在了
     */
    public static <T> void createBean(ApplicationContext applicationContext, String beanName, Class<T> beanClass, Map<String,Object> beanProperties, boolean singleton, String initMethod, String destroyMethod, boolean handleAutowireDependency){
        Assert.notNull(beanClass, "Parameter 'beanClass' can not be null!");
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        boolean canCreate = true;
        if(singleton){
            if(!StringUtils.isEmpty(beanName)){
                canCreate = !beanFactory.containsBean(beanName);
            }
        }
        boolean hasBeanName = !StringUtils.isEmpty(beanName);
        if(!hasBeanName){
            for(int i = 0; i < Integer.MAX_VALUE; i++){
                beanName = beanClass.getName() + "#" + i;
                if(!beanFactory.containsBean(beanName)){
                    break;
                }
            }
        }

        if(canCreate){
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
            if(!beanProperties.isEmpty()){
                for(Map.Entry<String,Object> entry : beanProperties.entrySet()){
                    beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
                }
            }
            if(!StringUtils.isEmpty(initMethod)){
                beanDefinitionBuilder.setInitMethodName(initMethod);
            }
            if(!StringUtils.isEmpty(destroyMethod)){
                beanDefinitionBuilder.setDestroyMethodName(destroyMethod);
            }
            beanDefinitionBuilder.setScope(singleton ? "singleton" : "prototype");
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            if(handleAutowireDependency){
                String[] existBeanNames = beanFactory.getBeanDefinitionNames();
                for(String existBeanName : existBeanNames){
                    Object existingBean = beanFactory.getBean(existBeanName);
                    beanFactory.autowireBean(existingBean); //process @Autowire dependency
                }
            }
            logger.info("Create and register bean[name = {}, class= {}] to spring container successfully.", beanName, beanClass);
        }else{
            logger.error("Create and register bean[name = {}, class= {}] to spring container failed：spring container already exists a bean with name '{}'.", beanName, beanClass, beanName);
        }
    }

    /**
     * 手动创建一个Bean
     * @param beanName
     * @param beanClass
     * @param beanProperties
     * @param singleton
     * @param initMethod
     * @param destroyMethod
     * @param handleAutowireDependency	 - 当前被创建的bean被其他已经存在的bean依赖需要autowire?
     * @return 如果return null则表示该名称的bean已经存在了
     */
    public static <T> void createBean(String beanName, Class<T> beanClass, Map<String,Object> beanProperties, boolean singleton, String initMethod, String destroyMethod, boolean handleAutowireDependency){
        createBean(applicationContext, beanName, beanClass, beanProperties, singleton, initMethod, destroyMethod, handleAutowireDependency);
    }

    /**
     * 手动创建一个Bean
     * @param beanName
     * @param beanClass
     * @param beanProperties
     * @param singleton
     * @param handleAutowireDependency	 - 当前被创建的bean被其他已经存在的bean依赖需要autowire?
     * @return 如果return null则表示该名称的bean已经存在了
     */
    public static <T> void createBean(String beanName, Class<T> beanClass, Map<String,Object> beanProperties, boolean singleton, boolean handleAutowireDependency){
        createBean(beanName, beanClass, beanProperties, singleton, null, null, handleAutowireDependency);
    }

    /**
     * 手动创建一个Bean
     * @param beanName
     * @param beanClass
     * @param beanProperties
     * @param singleton
     * @return 如果return null则表示该名称的bean已经存在了
     */
    public static <T> void createBean(String beanName, Class<T> beanClass, Map<String,Object> beanProperties, boolean singleton){
        createBean(beanName, beanClass, beanProperties, singleton, null, null, true);
    }

}

