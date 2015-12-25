package com.exam.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Configuration class which loads the properties from the file into memory.
 * 
 * @author shubhankar_roy
 *
 */
public class ApplicationConfiguration {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

	/**
	 * configurations loaded from the properties file.
	 */
	private Properties configuration = new Properties();
	
    /**
     * Singleton {@link ApplicationConfiguration} instance.
     */
    private final static ApplicationConfiguration INSTANCE = new ApplicationConfiguration();
    
    /**
     * @return instance of {@link ApplicationConfiguration}
     */
    public static ApplicationConfiguration getInstance() {
        return INSTANCE;
    }
 
    /**
     * Initialize properties file....
     * 
     * @param file
     */
    public void initialize(final String file) {
    	logger.info("Initialize properties file....");
    	configuration.clear();
        try(InputStream in = new FileInputStream(new File(file))) {
            configuration.load(in);
        } catch (IOException e) {
            logger.error("Error occurred on loading the file.", e);
        }
    }
    
    /**
     * @param key
     * @return value of the property key
     */
    public String get(final String key) {
        return (String) configuration.get(key);
    }
 
    /**
     * @param key
     * @param defaultValue
     * @return return value of the property key and if value is null then return defaultValue.
     */
    public String get(final String key, final String defaultValue) {
    	logger.debug("Get prpoerty by key: {}, default value: {}", key, defaultValue);
        return (String) configuration.getProperty(key, defaultValue);
    }
}