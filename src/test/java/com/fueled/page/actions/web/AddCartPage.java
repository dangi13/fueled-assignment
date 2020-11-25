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

public class AddCartPage {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddCartPage.class);

	private WebDriver driver;

	@FindBy(how = How.ID, using = "nav-cart")
	WebElement cartButton;

	@FindBy(how = How.CSS, using = "span.sc-product-price")
	List<WebElement> allPrices;
	
	@FindBy(how = How.CSS, using = "input[data-action='delete']")
	List<WebElement> deleteButton;
	
	@FindBy(how = How.CSS, using = "span.sc-price")
	WebElement cartTotalPrice;

	public AddCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * It will give the Contact Information present in box.
	 * 
	 * @return String
	 */
	public void openCart() {
		info("Opening the cart");
		waitForElementPresence("nav-cart_id", 10);
		click(cartButton);
	}

	public void makeCartAccToBudget(double priceLimit) {
		info("Making cart according to limit: " + priceLimit);
		waitForElementPresence("span.sc-price_css");
		String cartTotal = WebUtils.getElement("span.sc-price_css").getAttribute("innerText").trim().replaceAll(",", "").replaceAll("\\u00a0", "");
		String cleanPrice = cartTotal.replaceAll("^\\D+", "");
		System.out.println("Cart Total is : " + cleanPrice);
		double totalAmount = Double.valueOf(cleanPrice);
		while (totalAmount > priceLimit) {
			click(deleteButton.get(deleteButton.size() - 1));
			waitForElementPresence("span.sc-price_css");
			cartTotalPrice = WebUtils.getElement("span.sc-price_css");
			cartTotal = cartTotalPrice.getAttribute("innerText").trim().replaceAll(",", "").replaceAll("\\u00a0", "");
			cleanPrice = cartTotal.replaceAll("^\\D+", "");
			totalAmount = Double.valueOf(cleanPrice.trim());
		}
		
		info("Cart Amount after every item added or removed is : " + totalAmount);
	}
}
