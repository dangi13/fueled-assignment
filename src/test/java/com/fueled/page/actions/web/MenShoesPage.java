package com.fueled.page.actions.web;

import static com.fueled.reporter.ExtentReporter.info;
import static com.fueled.utils.selenium.WebUtils.click;
import static com.fueled.utils.selenium.WebUtils.waitForElementPresence;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fueled.utils.selenium.WebUtils;

/**
 * This class contains all actions/operations that will be performed on Login
 * page in Desktop web view.
 * 
 * @author Jaikant.lnu
 *
 */
public class MenShoesPage {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenShoesPage.class);
	private static int shoeCount = 1;

	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//span[.='Formal Shoes']/parent::a")
	WebElement formalShoes;

	@FindBy(how = How.XPATH, using = "//input[@name='s-ref-checkbox-BATA']")
	WebElement bataFilter;

	@FindBy(how = How.XPATH, using = "//a[@class='a-link-normal s-no-outline' and @target='_blank']")
	List<WebElement> allShoesOnPage;

	@FindBy(how = How.CSS, using = "select[id='native_dropdown_selected_size_name'] option[data-a-html-content='7 UK']")
	List<WebElement> availableShoeNumber;

	@FindBy(how = How.CSS, using = "select[id='native_dropdown_selected_size_name']")
	WebElement shoeSizeDropdown;

	@FindBy(how = How.ID, using = "add-to-cart-button")
	WebElement addCartButton;

	@FindBy(how = How.ID, using = "send2")
	WebElement signInButton;
	
	@FindBy(how = How.ID, using = "availability")
	WebElement availabilityMessage;
	
	@FindBy(how = How.ID, using = "huc-v2-order-row-confirm-text")
	WebElement addCartConfirmation;

	public MenShoesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * It will perform login operation with default credentials present in
	 * config.properties file.
	 */
	public void selectFormalShoesAndBataAsBrand() {
		info("Selecting Formal Shoes and Bata as brand..........");
		waitForElementPresence("//span[.='Formal Shoes']/parent::a_xpath", 10);
		click(formalShoes);
		waitForElementPresence("//input[@name='s-ref-checkbox-BATA']_xpath", 10);
		click(bataFilter);
	}

	/**
	 * It will perform logout from application
	 */
	public void addShoesToCart(int numberOfShoes) {
		info("Adding " + numberOfShoes + " shoes to cart");
		for (WebElement shoe : allShoesOnPage) {
			if (shoeCount > numberOfShoes) {
				break;
			}
			click(shoe);
			WebUtils.switchToNextTab();
			waitForElementPresence("select[id='native_dropdown_selected_size_name']_css");
			availableShoeNumber = WebUtils.getElements("select[id='native_dropdown_selected_size_name']_css");
			if (availableShoeNumber.size() == 1 && shoeCount <= 3) {
				shoeSizeDropdown = WebUtils.getElement("select[id='native_dropdown_selected_size_name']_css");
				String sizeToSearch = WebUtils.getElements("select[id='native_dropdown_selected_size_name'] option_css").stream().filter(option -> option.getAttribute("innerText").trim().contains("7")).findFirst().get().getAttribute("data-a-html-content");
				WebUtils.selectDropDownByVisibleText(shoeSizeDropdown, sizeToSearch);
				waitForElementPresence("availability_id", 20);
				WebElement addCartButton = WebUtils.getElement("add-to-cart-button_id");
				click(addCartButton);
				waitForElementPresence("huc-v2-order-row-confirm-text_id", 20);
				WebUtils.closeNextTab();
				shoeCount++;
			}
		}

	}

}
