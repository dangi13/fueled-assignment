package com.fueled.page.validators.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fueled.reporter.ExtentReporter;
import com.fueled.utils.common.CustomAssertion;

/** This class contains all validations related to Login Page when opened in Desktop web.
 * 
 * @author Jaikant
 *
 */
public class LoginValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginValidator.class);

	
	/** It will validate the invalid login credentials
	 * @param actual
	 * @param expected
	 */
	public void validateInvalidLogin(String actual, String expected) {
		CustomAssertion.assertEquals(actual, expected);
		 
		// this is just demo validation
		// simply, all of your validations should go here relted to invalid login to maintain clean code
	}

	/** It will validate the correct login credentials.
	 * @param actual
	 * @param expected
	 */
	public void validateSuccessLogin(String actual, String expected) {
		ExtentReporter.info("Validating successfull Login");
		CustomAssertion.assertContains(actual, expected);
	}

}
