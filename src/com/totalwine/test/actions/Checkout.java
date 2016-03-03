package com.totalwine.test.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Checkout {
	
	static WebDriver driver = new FirefoxDriver ();
	
	//ISP Checkout
	public static void ISPCheckout () throws InterruptedException {
		SiteAccess.ActionAccessSite(driver, "71.193.51.0"); //Sacramento
		//Access Wine PDP
		//Add to Cart
		//Access Beer PDP
		//Add to Cart
		//Access Spirits PDP
		//Add to Cart
		//Access Accessories PDP
		//Add to Cart
		//Checkout

	}
	
	//Ship Checkout
	public static void ShipCheckout () throws InterruptedException {
		SiteAccess.ActionAccessSite(driver, "66.230.105.38"); //Alaska
		//Access Wine PDP
		//Add to Cart
		//Access Accessories PDP
		//Add to Cart
		//Checkout
	}
	
	public static void main (String[] args) throws InterruptedException {
		ShipCheckout();
	}
}
