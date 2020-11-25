package com.fueled.tests.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fueled.core.BaseConfiguration;
import com.fueled.page.actions.web.AddCartPage;
import com.fueled.page.actions.web.MenShoesPage;
import com.fueled.page.actions.web.MenuPage;

public class AmazonTest extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonTest.class);

	private MenShoesPage shoePage;
	private MenuPage menuPage;
	private AddCartPage cartPage;

	@BeforeClass
	public void setUp() {
		menuPage = new MenuPage(driver);
		shoePage = new MenShoesPage(driver);
		cartPage = new AddCartPage(driver);
	}

	@Test(testName = "TC_01", description = "Add 3 bata shoes to cart on Amazon India")
	public void test_add_to_cart_on_amazon_india() {
		menuPage.openMenu();
		menuPage.selectMenShoesSection();
		shoePage.selectFormalShoesAndBataAsBrand();
		shoePage.addShoesToCart(3);
		cartPage.openCart();
		cartPage.makeCartAccToBudget(2000);
	}

}
