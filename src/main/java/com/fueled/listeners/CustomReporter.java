package com.fueled.listeners;

import static com.fueled.utils.common.CommonUtils.createDirectory;
import static com.fueled.utils.common.Constants.*;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import com.fueled.reporter.ExtentReporter;
import com.fueled.utils.common.CommonUtils;
import com.fueled.utils.selenium.WebUtils;

/**
 * It provides complete reporting of the suite execution.
 *
 */
public class CustomReporter implements IReporter, ITestListener, IExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomReporter.class);

	@Override
	public void onStart(ITestContext testContext) {
		ExtentReporter.onStart(testContext);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		ExtentReporter.onFinish(testContext);
	//	WebUtils.terminateBrowser();
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		ExtentReporter.onTestFail(testResult);
		LOGGER.error(LOG_DESIGN + "Test Case Failed: {}", testResult.getMethod().getMethodName());
		LOGGER.error(LOG_DESIGN + "Failure Reason : ", testResult.getThrowable());
		onTestCompletion(testResult, "FAILED");

	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		ExtentReporter.onTestSkip(testResult);
		LOGGER.info(LOG_DESIGN + "Test Case Skipped: {}", testResult.getMethod().getMethodName());
		onTestCompletion(testResult, "SKIPPED");

	}

	@Override
	public void onTestStart(ITestResult testResult) {
		LOGGER.info(
				"######################################## TEST CASE STARTED :  [{}] ###################################################################",
				testResult.getMethod().getMethodName());
		ExtentReporter.onTestStart(testResult);
		String testId = testResult.getMethod().getMethod().getAnnotation(Test.class).testName();
		String testDescription = testResult.getMethod().getDescription();
		String beautifiedDesc = String.format(
				"<table><tr><th>TC ID</th><th>Description</th></tr><tr><td>%s</td><td>%s</td></tr></table>", testId,
				testDescription);
		ExtentReporter.info(beautifiedDesc);

		LOGGER.info(LOG_DESIGN + "Execution Started For Test: {}", testResult.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		ExtentReporter.onTestPass(testResult);
		LOGGER.info(LOG_DESIGN + "Test Case Passed : {}", testResult.getMethod().getMethodName());
		onTestCompletion(testResult, "PASSED");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {

	}

	@Override
	public void onExecutionStart() {
		LOGGER.info("                            ##########################                                 ");
		LOGGER.info("############################ SUITE EXECUTION STARTED ################################");
		LOGGER.info("                            ##########################                                 ");

		ExtentReporter.onExceutionStart();

		createDirectory(TEST_RESULT_DIRECTORY_PATH);
		createDirectory(TEST_CASE_SCREENSHOTS_PATH);
		createDirectory(EXECUTION_REPORTS_PATH);
		createDirectory(CURRENT_DAY_EXECUTION_REPORT);

		LOGGER.info(LOG_DESIGN + "Test Result Directories created.");

	}

	@Override
	public void onExecutionFinish() {

		LOGGER.info("                            ##########################                                 ");
		LOGGER.info("############################ SUITE EXECUTION FINISHED ################################");
		LOGGER.info("                            ##########################                                 ");
	}

	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2) {

	}

	/**
	 * It will be used to perform the after test steps if any.
	 * 
	 * @param testResult
	 */
	private void onTestCompletion(ITestResult testResult, String testStatus) {
		ExtentReporter.setExecutionTime(testResult);
		String testCaseName = testResult.getMethod().getMethodName();
		ITestContext context = testResult.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute(DRIVER);
		String screenShotPath = TEST_CASE_SCREENSHOTS_PATH + File.separatorChar + testCaseName
				+ CommonUtils.getCurrentDateWithTime().replaceAll(":", "") + PNG_EXTENSION;
		WebUtils.captureScreenshot(driver, screenShotPath);
		ExtentReporter.attachScreenshot(screenShotPath);
		LOGGER.info(" TEST CASE [{}] : {} ", testCaseName, testStatus);
		LOGGER.info("");
	}
}
