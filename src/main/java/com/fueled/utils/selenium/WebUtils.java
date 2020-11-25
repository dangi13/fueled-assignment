package com.fueled.utils.selenium;



import static com.fueled.utils.common.Constants.LOG_DESIGN;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fueled.utils.common.Config;

/**
 * This class is responsible for performing all required user actions to
 * automate a web application. It is generally made for web applications that
 * run on desktop(e.g Windows/Mac etc.) browsers. Some methods might work for
 * mobile devices also but complete support is not present.
 *
 */
public class WebUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);
	public static WebDriver driver = null;

	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void setDriver(WebDriver driver) {
		WebUtils.driver = driver;
	}
	
	
	/**
	 *  It will refresh the current browser tab.
	 */
	public static void refreshBrowser() {
		LOGGER.info(LOG_DESIGN +"Refreshing the browser...");
		driver.navigate().refresh();
	}
	
	/** It will switch to the frame element.
	 * @param element
	 */
	public static void switchToFrame(WebElement element) {
		LOGGER.info(LOG_DESIGN +"Switching to iframe {}", element);
		driver.switchTo().frame(element);
	}
	
	public static void switchToDefaultFrame() {
		LOGGER.info(LOG_DESIGN +"Switching to default content.");
		driver.switchTo().defaultContent();
	}
	/**
	 *  It will refresh the current browser tab by getting its current URL.
	 */
	public static void refreshWithCurrentUrl() {
		LOGGER.info(LOG_DESIGN +"Refreshing the browser...");
		driver.get(driver.getCurrentUrl());
	}
	
	/**
	 *  It will refresh the current browser by hitting F5 key.
	 */
	public static void refreshWithF5Key() {
		LOGGER.info(LOG_DESIGN +"Refreshing the browser...");
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
	}
	
	/** It will open a new blank tab in browser.
	 * 
	 */
	public static void openNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
	}
	
	
	/** It will return the current URL of the page.
	 * @return Current URL
	 */
	public static String getCurrentURL() {
		
		return driver.getCurrentUrl();
	}
	
	/** It will close all other tabs except the provided one.
	 * @param originalHandle you can get it by using driver.getWindowHandle();
	 */
	public static void closeOtherTabs(String originalHandle) {
	    for(String handle : driver.getWindowHandles()) {
	        if (!handle.equals(originalHandle)) {
	            driver.switchTo().window(handle);
	            driver.close();
	        }
	    }
	}
	
	/**
	 *  It will close the next tab opened other then original tab.
	 */
	public static void closeNextTab() {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	    driver.close();
	    driver.switchTo().window(tabs2.get(0));
	}
		

	/**
	 *  It will switch to the 2nd tab present.
	 */
	public static void switchToNextTab() {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	}
	
	/**
	 *  It will switch to 1st tab.
	 */
	public static void switchToPreviousTab() {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(0));
	}
	
	/**
	 * It will get element.
	 * 
	 * @param locator
	 * @return WebElement
	 */
	public static WebElement getElement(String locator) {
		String locatorValue = isNull(Config.getProperty(locator))? locator : Config.getProperty(locator).trim();
		LOGGER.info(LOG_DESIGN + "Getting element for :-- [{}] : [{}]", locator, locatorValue);
		WebElement element = null;
		try {
			element = driver.findElement(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return element;
	}
	
	/**
	 * It will get list of elements of a specific locator.
	 * @param locator
	 * @return  List<WebElement>
	 */
	public static List<WebElement> getElements(String locator) {
		String locatorValue = isNull(Config.getProperty(locator))? locator : Config.getProperty(locator).trim();
		LOGGER.info(LOG_DESIGN + "Getting elements for :-- [{}] : [{}]", locator, locatorValue);
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return elements;
	}
	
	/** It will return the element using its parent element.
	 * @param parentElement
	 * @param locator
	 * @return WebElement
	 */
	public static WebElement getElement(WebElement parentElement, String locator) {
		String locatorValue = isNull(Config.getProperty(locator))? locator : Config.getProperty(locator);
		LOGGER.info(LOG_DESIGN + "Getting element for :-- [{}] : [{}]", locator, locatorValue);
		WebElement element = null;
		try {
			element = parentElement.findElement(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return element;
	}
	
	/** It will return list of elements using parent element.
	 * @param parentElement
	 * @param locator
	 * @return List<WebElement>
	 */
	public static List<WebElement> getElements(WebElement parentElement, String locator) {
		String locatorValue = isNull(Config.getProperty(locator))? locator : Config.getProperty(locator);
		LOGGER.info(LOG_DESIGN + "Getting elements for :-- [{}] : [{}]", locator, locatorValue);
		List<WebElement> elements = null;
		try {
			elements = parentElement.findElements(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return elements;
	}
	
	/**For WebElement : It will return only visible element (neglecting hidden elements of same locator).
	 * @param locator
	 * @param timeOut
	 * @return WebElement
	 */
	public static WebElement getDisplayedWebElement(String locator, long timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) .withTimeout(Duration.ofSeconds(timeOut))
		        												 .pollingEvery(Duration.ofSeconds(3))
		        												 .ignoring(org.openqa.selenium.NoSuchElementException.class)
																 .ignoring(NoSuchElementException.class);   
		 WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
					LOGGER.info(LOG_DESIGN + "Polling for element untill displayed for :-- [{}]", locator);
					List<WebElement> elements = driver.findElements(getByObject(locator));
					return elements.stream().filter(element -> element.isDisplayed()).findFirst().get();
			}
		});

		return element;
	}
	
	/** For WebElement : It will return only visible elements (neglecting hidden elements of same locator)
	 * @param locator
	 * @param timeOut
	 * @return List<WebElement>
	 */
	public static List<WebElement> getDisplayedWebElements(String locator, long timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) .withTimeout(Duration.ofSeconds(timeOut))
		        												 .pollingEvery(Duration.ofSeconds(1))
		        												 .ignoring(org.openqa.selenium.NoSuchElementException.class)
																 .ignoring(NoSuchElementException.class);   
		 List<WebElement> elements = (List<WebElement>)wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				LOGGER.info(LOG_DESIGN + "Polling for element untill displayed for :-- [{}]", locator);
				List<WebElement> elements = driver.findElements(getByObject(locator));
				return elements.stream().filter(element -> element.isDisplayed()).collect(Collectors.toList());
				}
				
			});
		 
		 	return elements;
		};
		
		 
	
	/** It will give the object of "By" : 
	 * @param locator
	 * @return By object
	 */
	public static By getByObject(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
		locatorValue = locatorValue.trim();
		By byObj = null;
		if (locator.endsWith("_xpath")) {
			byObj = By.xpath(locatorValue.replaceAll("_xpath", ""));
		} else if (locator.endsWith("_css")) {
			byObj = By.cssSelector(locatorValue.replaceAll("_css", ""));
		} else if (locator.endsWith("_id")) {
			byObj = By.id(locatorValue.replaceAll("_id", ""));
		} else if (locator.endsWith("_linkText")) {
			byObj = By.linkText(locatorValue.replaceAll("__linkText", ""));
		} else if (locator.endsWith("_name")) {
			byObj = By.name(locatorValue.replaceAll("_name", ""));
		}
		
		return byObj;
		
	}
	
	/**  It just check that an element is present on the DOM of a page. 
	 * @param locator
	 * @param seconds
	 */
	public static void waitForElementPresence(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for presence of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(getByObject(locator)));
	}
	
	/**  It just check that an element is present on the DOM of a page. 
	 * @param locator
	 */
	public static void waitForElementPresence(String locator) {
		LOGGER.info(LOG_DESIGN + "waiting for presence of element [{}] for maxTimeout", locator);
		WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(Config.getProperty("maxTimeOut")));
		wait.until(ExpectedConditions.presenceOfElementLocated(getByObject(locator)));
	}
	
	/** It will check that an element is present on the DOM of a page and visible. 
	 * @param locator
	 * @param seconds
	 */
	public static void waitForElementVisibility(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for visibility of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
		

	}
	
	/** It will check that an element is present on the DOM of a page and visible. 
	 * @param locator
	 * @param seconds
	 */
	public static void waitForElementInVisibility(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for visibility of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getByObject(locator)));

	}
	
	/** It will check that an element is present on the DOM of a page and visible. 
	 * @param locator
	 */
	public static void waitForElementVisibilityWithMinTimeOut(String locator) {
		LOGGER.info(LOG_DESIGN + "waiting for visibility of element [{}] for minTimeout", locator);
		WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(Config.getProperty("minTimeOut")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
	}
	
	/** It will check that an element is present on the DOM of a page and visible. 
	 * @param locator
	 */
	public static void waitForElementVisibilityWithMaxTimeOut(String locator) {
		LOGGER.info(LOG_DESIGN + "waiting for visibility of element [{}] for maxTimeout", locator);
		WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(Config.getProperty("maxTimeOut")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
	}
	
	/** It will hard wait for the given seconds.
	 * @param seconds
	 */
	public static void sleep(int seconds) {
		LOGGER.info(LOG_DESIGN + "Waiting for {} seconds", seconds);
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			LOGGER.info(LOG_DESIGN + "Exception occurred while waiting for {} seconds ", seconds);
		}
		
	}
	/**
	 * It will click on a given locator.
	 * 
	 * @param locator
	 */
	public static void click(String locator) {
		try {
			WebElement element = getElement(locator);
			highlightWebElement(element);
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}
	
	/**
	 * It will click on a given locator.
	 * 
	 * @param locator
	 */
	public static void click(WebElement element) {
		try {
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}]", element);
			highlightWebElement(element);
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}

	/** It will navigate to the specified URL.
	 * @param URL
	 */
	public static void navigateToURL(String URL) {
		LOGGER.info(LOG_DESIGN + "Navigating to URL : [{}]", URL);
		driver.navigate().to(URL);
		
	}
	
	/** It will give the page title.
	 * @return
	 */
	public static String getPageTitle() {
		String pageTitle  = driver.getTitle();
		LOGGER.info(LOG_DESIGN + "Page Title is : [{}]", pageTitle);
		
		return pageTitle;
		
	}
	
	/** It will give the text present in element.
	 * @return element's text value
	 */
	public static String getText(String locator) {
		WebElement element = getElement(locator);
		highlightWebElement(element);
		String elementText = element.getText();
		LOGGER.info(LOG_DESIGN + "Text found for : [{}] is : [{}]", locator, elementText);
		
		return elementText;
		
	}
	
	/** It will get an element's text using any attribute.
	 * For e.g attribute can be : innerText,textContent,title etc as defined in the element html code
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public static String getTextUsingAttribute(String locator, String attribute) {
		WebElement element = getElement(locator);
		highlightWebElement(element);
		String elementText = element.getAttribute(attribute);
		LOGGER.info(LOG_DESIGN + "Text found for : [{}] is : [{}]", locator, elementText);
		
		return elementText;
		
	}
	
	/** It will get InnerText of element using JavaScript.
	 * @param element
	 * @return innerText of element.
	 */
	public static String getInnerText(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Getting innerText using javascript, WebElement : [{}]", element);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;",element);
	}
	
	/** It will get InnerText of element using JavaScript.
	 * @param locator
	 * @return innerText of element.
	 */
	public static String getInnerText(String locator) {
		WebElement element = getElement(locator);
		LOGGER.info(LOG_DESIGN + "Getting innerText using javascript, WebElement : [{}]", element);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;",element);
	}
	
	/** It will get textContent of element using JavaScript.
	 * @param element
	 * @return textContent of element.
	 */
	public static String getTextContent(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Getting textContent using javascript, WebElement : [{}]", element);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;",element);
	}
	
	public static String getAttributeUsingJs(String locator, String attribute) {
		LOGGER.info(LOG_DESIGN + "Getting [{}] attribute using javascript for locator [{}] :" , attribute, locator);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getAttribute('" + attribute + "');", getElement(locator));
	}
	
	/** It enters the value in text box.
	 * @param locator
	 * @param text
	 */
	public static void enterText(String locator, String text) {
		WebElement element = getElement(locator);
		highlightWebElement(element);
		element.clear(); // clearing if any text is present in text box.
		LOGGER.info(LOG_DESIGN + "Entering text for : [{}] ::   Text is : [{}]", locator, text);
		element.sendKeys(text);
		
	}
	
	/** It enters the value in text box.
	 * @param locator
	 * @param text
	 */
	public static void enterText(WebElement element, String text) {
		highlightWebElement(element);
		element.clear(); // clearing if any text is present in text box.
		LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", element, text);
		element.sendKeys(text);
		
	}
	
	/** It enters the value in text box.
	 * @param locator
	 * @param text
	 */
	public static void enterTxt(WebElement element, String text) {
		highlightWebElement(element);
		LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", element, text);
		element.sendKeys(text);
	}
		
	
	
	/** It will clear the text present in text box.
	 * @param locator
	 */
	public static void clearTextBox(String locator) {
		WebElement element = getElement(locator);
		highlightWebElement(element);
		element.clear();
		LOGGER.info(LOG_DESIGN + "Cleared text for : [{}]", locator);
		
	}
	
	
	

	/** It enters the value in text box.
	 * @param locator
	 * @param text
	 */
	public static void enterTextUsingActions(String locator, String text) {
		WebElement element = getElement(locator);
		highlightWebElement(element);
		element.clear();
		LOGGER.info(LOG_DESIGN  + "Entering text for : [{}] ::   Text is : [{}]", locator, text);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(text);
		actions.build().perform();
		
	}
	
	/** It enters the value in text box.
	 * @param locator
	 * @param text
	 */
	public static void enterTextUsingActions(WebElement element, String text) {
		highlightWebElement(element);
		LOGGER.info(LOG_DESIGN  + "Entering text for : [{}] ::   Text is : [{}]", element, text);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(text);
		actions.build().perform();
	}
	
    /**
     * It focuses on given web element.
     * 
     * @param driver
     * @param element
     */
    public static void jsFocus(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].focus();", element);
    }
    
    /**
     * It clicks on given web element using javascript.
     * 
     * @param element
     */
    public static void jsClick(WebElement element) {
    	LOGGER.info(LOG_DESIGN + "Clicking on element : {} using javascript", element);
    	highlightWebElement(element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }
    
    /**
     * It clicks on given locator using javascript.
     * 
     * @param element
     */
    public static void jsClick(String locator) {
    	WebElement element = getElement(locator);
        highlightWebElement(element);
        LOGGER.info(LOG_DESIGN + "Clicking on locator : {} using javascript", locator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }
    
	/**
	 * It scrolls to the given WebElement.
	 * 
	 * @param driver
	 * @param element
	 * @return WebElement
	 */
    public static WebElement scrollingToElementofAPage(WebElement element) {
    	LOGGER.info(LOG_DESIGN + "Scrolling to element : {} ", element);
    	highlightWebElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
       
        return element;
    }
    
    /** It will highlight the web element
     * @param element
     */
    public static void highlightWebElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'background:#ffffb3; border:3px solid green;');", element);
    }
    
	/**
	 * This method copies the content to system Clipboard and than paste it. i.e it
	 * just performs copy paste operation like (Ctrl C + Ctrl V)
	 * 
	 * @param element
	 * @param stringToBePasted
	 */
	public static void copyPaste(WebElement element, String stringToBePasted) {
		LOGGER.info(LOG_DESIGN+ "Clearing any text if present..");
		element.clear();
		element.click();
		LOGGER.info(LOG_DESIGN + "copy pasting : [{}] .", stringToBePasted);
		StringSelection stringSelection = new StringSelection(stringToBePasted);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			LOGGER.error(LOG_DESIGN + "!!!!!!!! Exception occured while copy pasting the given content....: {}", e.getMessage());
		}
		
	}
	
	/** It will click on element using Actions class.
	 * @param element
	 * @param seconds
	 */
	public static void actionClick(WebElement element, long seconds) {
			highlightWebElement(element);
			LOGGER.info(LOG_DESIGN + "Clicking on WebElement using Actions class, element : [{}]", element);
			WebDriverWait driverWait = new WebDriverWait(driver, seconds);
			driverWait.until(ExpectedConditions.elementToBeClickable(element));
			Actions actions = new Actions(driver);
			actions.click(element).build().perform();
		    }
	
	/** It will click on element using Actions class.
	 * @param element WebElement
	 */
	public static void actionClick(WebElement element)  {
		highlightWebElement(element);
		LOGGER.info(LOG_DESIGN + "Clicking on WebElement using Actions class, element : [{}]", element);
		WebDriverWait driverWait = new WebDriverWait(driver, Long.valueOf(Config.getProperty("maxTimeOut")));
		driverWait.until(ExpectedConditions.elementToBeClickable(element));
		Actions actions = new Actions(driver);
		actions.click(element).build().perform();
	}
	
	/** It will click on element without waiting for it to be clickable using Actions class.
	 * @param element WebElement
	 */
	public static void actionPress(WebElement element)  {
		highlightWebElement(element);
		LOGGER.info(LOG_DESIGN + "Clicking on WebElement using Actions class, element : [{}]", element);
		Actions actions = new Actions(driver);
		actions.click(element).build().perform();
	}
	
	/** It will select the value from dropdown using value attribute present in HTML tag.
	 * @param element
	 * @param value
	 */
	public static void selectDropDownByValue(WebElement element, String value) {
		LOGGER.info(LOG_DESIGN + "Selecting value [{}] from dropdown, element : [{}]", value, element);
		Select select = new Select(element);
		select.selectByValue(value);
	}
	
	/** It will select the dropdown value using its index.
	 * @param element
	 * @param value
	 */
	public static void selectDropDownByIndex(WebElement element, int index) {
		LOGGER.info(LOG_DESIGN + "Selecting index [{}] from dropdown, element : [{}]", index, element);
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	/** It will select the value using current text visible on dropdown.
	 * @param element
	 * @param value
	 */
	public static void selectDropDownByVisibleText(WebElement element, String visibleText) {
		LOGGER.info(LOG_DESIGN + "Selecting visibleText [{}] from dropdown, element : [{}]", visibleText, element);
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}
	
	/** It will check if element is enabled or not.
	 * @param element
	 * @return enabled status
	 */
	public static boolean isElementEnabled(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Checking if element is enabled, element : [{}]", element);
		
		return element.isEnabled();
	}
	
	/** It will check if element is selected or not.
	 * @param element
	 * @return enabled status
	 */
	public static boolean isElementSelected(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Checking if element is selected, element : [{}]", element);
		
		return element.isSelected();
	}
	
	/** It will check if element is selected or not.
	 * @param element
	 * @return enabled status
	 */
	public static boolean isElementDisplayed(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Checking if element is displayed, element : [{}]", element);
		
		return element.isDisplayed();
	}
	
	/** It will perform mouse hover operation on the WebElement.
	 * @param elementToHover
	 */
	public static void mouseHover (WebElement elementToHover) {
		LOGGER.info(LOG_DESIGN + "Mouse hovering on element , element : [{}]", elementToHover);
		Actions hover = new Actions(driver);
		hover.moveToElement(elementToHover);
		hover.build();
		hover.perform();
	}
	
	/**
	 * It will perform mouse hover operation on the WebElement using javascript.
	 * 
	 * @param elementToHover
	 */
	public static void mouseHoverUsingJs(WebElement elementToHover) {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);}else if(document.createEventObject){ arguments[0].fireEvent('onmouseover');}";
		((JavascriptExecutor) driver).executeScript(mouseOverScript, elementToHover);
	}
	

	/** It will get the first occurrence of the particular text and click it
	 * e.g. under any locator - get the value of the attribute  and find its first occurrence and click it.
	 * @param locator
	 * @param timeOut
	 * @param attributeName
	 * @param text
	 * 
	 */
	public static void clickFirstDisplayedElement(String locator, long timeOut, String attributeName, String text ) {
		List<WebElement> options = WebUtils.getDisplayedWebElements(locator, timeOut);
		WebElement OptionToSelect = options.stream().filter(option -> option.getAttribute(attributeName).contains(text)).findFirst().get();
		WebUtils.click(OptionToSelect);
	}
	
	/** It will first close the opened browser and then kill the chromedriver process.
	 * 
	 */
	public static void terminateBrowser() {
		if (nonNull(driver)) {
		//	driver.close();
			driver.quit();
		}
	}
	
	/** It will get selected value from dropdown
	 * @param element
	 */
	public static String getSelectedValueInDropDown(WebElement element) {
		Select select = new Select(element);
		String selectedValueFromDropdown = select.getFirstSelectedOption().getText();
		LOGGER.info(LOG_DESIGN + "Selected value from dropdown [{}] , is [{}]", element, selectedValueFromDropdown);
		return selectedValueFromDropdown;
	}
	
	/** It will generate random number based on current System Date and Time in form of ddhhmmss.
	 * e.g : if current date and time is 1stMay 2019 and time is 04:13:20, then it will return values 01041320
	 * @param 
	 * @return
	 */
	public static String generateRandomNoInDateAndTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("ddhhmmss");
		Date date = new Date();
		String randomnoInformofdate = formatter.format(date);
		
		return randomnoInformofdate;
	}
	
	/** It will re attempt the click if StaleElementReferenceException exception occurs.
	 * @param WebElement 
	 * @return
	 */
	public static boolean retryingClick(WebElement element) {
		LOGGER.info("Inside retryingFindClick method");
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 2) {
	        try {
	        	actionClick(element);
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	    return result;
	}
	
	/** It will capture the screenshot in the browser.
	 * @param webdriver
	 * @param screenshotPath
	 */
	public static void captureScreenshot(WebDriver webdriver, String screenshotPath) {
		LOGGER.info(LOG_DESIGN + "Capturing Screenshot..");
		try {
			
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenshotPath);
			FileUtils.copyFile(srcFile, destFile);
			LOGGER.info(LOG_DESIGN + "Screenshot Captured. : {}", screenshotPath);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception while Copying Screen Shot to Results folder and exception details are: {}", e.getMessage());

		}

	}
	
}
