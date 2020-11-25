package com.fueled.page.actions.web;

import static com.fueled.reporter.ExtentReporter.info;
import static com.fueled.utils.selenium.WebUtils.click;
import static com.fueled.utils.selenium.WebUtils.waitForElementPresence;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** This class contains all actions/operations that will be performed on Home page in Desktop web view.
 * 
 * @author Jaikant.lnu
 *
 */
public class MenuPage {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuPage.class);

	private WebDriver driver;

	@FindBy(how = How.ID, using = "nav-hamburger-menu")
	WebElement menuHamburger;
	
	@FindBy(how = How.XPATH, using = "//div[@id='hmenu-content']//ul//li[contains(.,'Men')]//i")
	WebElement menFashionSection;
	
	@FindBy(how = How.XPATH, using = "//a[.='Shoes']")
	WebElement menShoeSection;

	public MenuPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	/**
	 * It will open the login page.
	 */
	public void openMenu() {
		info("Opening menu ..........");
		waitForElementPresence("nav-hamburger-menu_id", 10);
		click(menuHamburger);
	}	
	
	/**
	 * It will open the login page.
	 */
	public void selectMenShoesSection() {
		info("Opening Mens shoes section ..........");

		waitForElementPresence("//div[@id='hmenu-content']//ul//li[contains(.,'Men')]//i_xpath", 10);
		click(menFashionSection);
		
		waitForElementPresence("//a[.='Shoes']_xpath", 10);
		click(menShoeSection);
	}

}
