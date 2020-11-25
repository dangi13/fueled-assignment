package com.fueled.utils.common;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.fueled.reporter.ExtentReporter;

/** It contains all methods for assertion or validation with added log info.
 *
 *	@author Jaikant
 */
public class CustomAssertion {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAssertion.class);

	
	/** It will make assertion of two strings.
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final String actual, final String expected) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		ExtentReporter.info(String.format("Validating :- Actual [%s] : Expected [%s]", actual, expected));
		Assert.assertEquals(actual, expected);
	}
	
	/** It will make assertion of two strings.
	 * @param actual
	 * @param expected
	 */
	public static void assertContains(final String actual, final String expected) {
		LOGGER.info("Validating :- Actual [{}] contains : " + "Expected [{}]", actual, expected);
		ExtentReporter.info(String.format("Validating :- Actual [%s] contains : Expected [%s]", actual, expected));
		Assert.assertTrue(actual.contains(expected));
	}

	
	/** It will make assertion of two strings.
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final String actual, final String expected, final String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		ExtentReporter.info(String.format("Validating :- Actual [%s] : Expected [%s]", actual, expected));
		Assert.assertEquals(actual, expected);
	}
	
	/**
	 * It will make assertion of two boolean.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final boolean actual, final boolean expected, final String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		ExtentReporter.info(String.format("Validating :- Actual [%s] : Expected [%s]", actual, expected));
		Assert.assertEquals(actual, expected);
	}
	
	/** It will make assertion of true statement.
	 * @param actual
	 */
	public static void assertTrue(final boolean actual, final String message) {
		LOGGER.info("Validating :- Actual [{}] : as assertTrue", actual);
		ExtentReporter.info(String.format("Validating :- Actual [%s] : as assertTrue", actual));
		Assert.assertTrue(actual);
	}
	
	/** It will make assertion of false statement.
	 * @param actual
	 */
	public static void assertFalse(final boolean actual, final String message) {
		LOGGER.info("Validating :- Actual [{}] : as assertFalse", actual);
		Assert.assertFalse(actual);
	}


	/** It will make assertion of two list.
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(List<String> actual, List<String> expected, String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		ExtentReporter.info(String.format("Validating :- Actual [%s] : Expected [%s]", actual, expected));
		Assert.assertEquals(actual, expected);
	}
	
}
