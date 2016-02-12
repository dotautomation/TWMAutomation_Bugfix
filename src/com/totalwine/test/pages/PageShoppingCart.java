package com.totalwine.test.pages;

import org.openqa.selenium.By;

public class PageShoppingCart {
	public static final By CheckOutButton = By.cssSelector("button.chkout-btn");
	public static final By ZipCodeField = By.id("zipCode");
	public static final By PromoCodeSubmitButton = By.cssSelector("input.anVoucherForm");
	public static final By QuantityText = By.cssSelector("input.cart-qty.numonly");
}
