package com.exam.config;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Check for changes in the configuration file.
 * 
 * @author shubhankar_roy
 *
 */
class ConfigurationChangeListener implements Runnable {
	/**
	 * logger instance
	 */
	private final Logger logger = LoggerFactory.getLogger(ConfigurationChangeListener.class);
    /**
     * Configuration file name.
     */
    private String configFileName;
    
    /**
     * Configuration file absolute path.
     */
    private final String fullFilePath;
    /**
     * Check if the application is still running.
     */
    private boolean appRunning = true;
 
    /**
     * Constructor, which initializes with path of the configuration file.
     * 
     * @param filePath
     */
    public ConfigurationChangeListener(final String filePath) {
        this.fullFilePath = filePath;
    }
 
    public void run() {
        try {
            register(this.fullFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Register the configuration file.
     * 
     * @param fileName
     * @throws IOException
     */
    private void register(final String fileName) throws IOException {
    	logger.info("Registering the configuration file: {}", fileName);
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
		final int lastIndex = absolutePath.lastIndexOf(File.separatorChar);
        String dirPath = absolutePath.substring(0, lastIndex + 1);
        this.configFileName = fileName;
		if(file.exists()) {
	        configurationChanged(fileName);
	        startWatcher(dirPath, fileName);
        } else {
        	logger.error("Properties File Does not Exists in path: {}, file name: {}", dirPath, fileName);
        }
    }
 
    /**
     * Starts watching the configuration file.
     * 
     * @param dirPath
     * @param file
     * @throws IOException
     */
    private void startWatcher(String dirPath, String file) throws IOException {
    	logger.debug("Start watching the configuration file.");
        final WatchService watchService = FileSystems.getDefault()
                .newWatchService();
        Path path = Paths.get(dirPath);
        path.register(watchService, ENTRY_MODIFY);
 
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    watchService.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
 
        WatchKey key = null;
        while (appRunning) {
            try {
                key = watchService.poll(5000, TimeUnit.MILLISECONDS);
                if(key != null) {
	                for (WatchEvent< ?> event : key.pollEvents()) {
	                    if (event.context().toString().equals(configFileName)) {
	                        configurationChanged(dirPath + file);
	                    }
	                }
	                boolean reset = key.reset();
	                if (!reset) {
	                    logger.debug("Could not reset the watch key.");
	                    break;
	                }
                }
            } catch (Exception e) {
            	logger.error("Exception occurred in watch service.", e);
            }
        }
    }
    
    /**
     * Shutdown WatchService.
     */
    public void shutDown() {
    	appRunning = false;
    }
 
    /**
     * Refresh the configurations.
     * 
     * @param file
     */
    public void configurationChanged(final String file) {
        logger.info("Refreshing the configuration.");
        ApplicationConfiguration.getInstance().initialize(file);
    }
}