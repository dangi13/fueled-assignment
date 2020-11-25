package com.fueled.utils.common;


import static com.fueled.utils.common.Constants.CONFIG_FOLDER_PATH;
import static com.fueled.utils.common.Constants.LOG_DESIGN;
import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** SINGLETON : This class is responsible for loading configuration from properties files.
 * 
 * @author Jaikant
 *
 */
public class Config {

	// to make this singleton
	private Config() {

	}

	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	private static final Properties PROPERTIES = new Properties();

	static {
		loadPropertyFiles(CONFIG_FOLDER_PATH);
	}

	/**
	 * It will load all the properties files present in config folder.
	 * 
	 * @param configFolderPath
	 */
	private static void loadPropertyFiles(String configFolderPath) {
		File folder = new File(configFolderPath);
		File[] files = folder.listFiles();
		for (File file : files) {
			String filePath = file.getAbsolutePath();
			if (filePath.endsWith(".properties")) {
				loadPropertiesFile(file.getAbsolutePath());
			}
		}
		LOGGER.info(LOG_DESIGN + "All property configuration files loaded");
	}

	/**
	 * It will load the given properties file
	 * 
	 * @param filePath
	 */
	private static void loadPropertiesFile(String filePath) {

		LOGGER.info(LOG_DESIGN + "Loading properties file : {} ", filePath);
		try {
			InputStream input = new FileInputStream(filePath);
			PROPERTIES.load(input);
			if (nonNull(input)) {
				input.close();
			}
		} catch (IOException e) {
			LOGGER.error(LOG_DESIGN + "!!!! Exception while loading properties file : {} ", e.getMessage());
		}
	}

	/**
	 * It will give the property value present in properties file.
	 * 
	 * @param propertyName
	 * @return property value
	 */
	public static String getProperty(String propertyName) {

		return PROPERTIES.getProperty(propertyName);
	}

}
