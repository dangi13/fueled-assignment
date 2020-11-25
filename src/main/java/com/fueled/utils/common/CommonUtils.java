package com.fueled.utils.common;

import static com.fueled.utils.common.Constants.LOG_DESIGN;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 *  It will contain all the common utilities.
 *  
 *  @author Jaikant
 *
 */
public class CommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * It creates directory or folder at provided path
	 * 
	 * @param directoryPath
	 */
	public static void createDirectory(String directoryPath) {
		LOGGER.info(LOG_DESIGN + "Creating directory : {}", directoryPath);
		File directory = new File(directoryPath);
		 if (!directory.exists()) directory.mkdirs();
	}
	
	
	/** It will give current date.
	 * @return current date  e.g: 2019-03-13  yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String currentDate = formatter.format(LocalDate.now());
		
		return currentDate;
	}
	
	/** It will give the current date with time. 
	 * @return current date time e.g : 2019-03-13 12:42:48   yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateWithTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = formatter.format(LocalDateTime.now());
		
		return currentDateTime;
	}
}
