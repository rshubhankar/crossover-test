package com.exam.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Factory class which provides objects from Application Context.
 * 
 * @author shubhankar_roy
 *
 */
public class DomainFactory {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(DomainFactory.class);
	/**
	 * Singleton {@link DomainFactory} instance
	 */
	private final static DomainFactory FACTORY_INSTANCE = new DomainFactory();
	
	/**
	 * Application context instance.
	 */
	private ApplicationContext context;
    
    /**
     * @return instance of {@link DomainFactory}
     */
    public static DomainFactory getInstance() {
        return FACTORY_INSTANCE;
    }
    
    /**
     * @param context
     */
    void setContext(ApplicationContext context) {
		this.context = context;
	}
    
    /**
     * Get object by class.
     * 
     * @param clazz
     * @return object from application context
     */
    public <T> T getBean(final Class<T> clazz) {
    	logger.debug("Get Object of class: {}", clazz);
        return context.getBean(clazz);
    }
 
    /**
     * Get object by name.
     * 
     * @param beanName
     * @return object from application context
     */
    @SuppressWarnings("unchecked")
	public <T> T getBean(final String beanName) {
    	logger.debug("Get Object by name: {}", beanName);
        return (T) context.getBean(beanName);
    }
    
    /**
     * Get object by name of class clazz.
     * 
     * @param beanName
     * @param clazz
     * @return object from application context
     */
    public <T> T getBean(final String beanName, final Class<T> clazz) {
    	logger.debug("Get Object by name of class: {} : {}", beanName, clazz);
        return context.getBean(beanName, clazz);
    }
}
