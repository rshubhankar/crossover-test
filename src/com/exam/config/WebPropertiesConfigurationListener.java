package com.exam.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class looks for the configuration file and loads it in memory.
 * 
 * @author shubhankar_roy
 *
 */
public final class WebPropertiesConfigurationListener implements ServletContextListener {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(WebPropertiesConfigurationListener.class);
	/**
	 * Checks for any changes in the configuration file
	 */
	private ConfigurationChangeListener listener;
	
	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath("/");
		logger.info("Real Path " + realPath);
		String applicationName = getPropertiesFileName(realPath, 1);
		logger.info("File name " + applicationName);
		String fileName = applicationName + "-web.properties";
		File file = new File(fileName);
		if (!file.exists()) {
			applicationName = getPropertiesFileName(realPath, 2);
			fileName = applicationName + "-web.properties";
		}
		logger.debug("Final Customer Name " + applicationName);
		sce.getServletContext().setAttribute("applicationName", applicationName);
		listener = new ConfigurationChangeListener(fileName);
		new Thread(listener).start();
	}

	private String getPropertiesFileName(String path, int lastIndex) {
		String propertiesFileName;
		try {
			StringTokenizer strToken = new StringTokenizer(path, File.separator);
			List<String> arr = new ArrayList<String>();
			while (strToken.hasMoreElements()) {
				arr.add((String) strToken.nextElement());
			}
			propertiesFileName = (String) arr.get(arr.size() - lastIndex);
		} catch (Exception e) {
			e.printStackTrace();
			propertiesFileName = "";
		}
		return propertiesFileName;
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		if(listener != null) {
			logger.debug("Shutting the configuration change listener.");
			listener.shutDown();
		}
	}
}
