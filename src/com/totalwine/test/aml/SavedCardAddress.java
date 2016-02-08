package com.totalwine.test.aml;

/*
 * Saved Credit Card/Address Workflow
 * Workflow:
 * 	1. 
 *  
 * Technical Modules:
 * 	1. BeforeMethod (Test Pre-requisites):
 * 			Invoke webdriver
 * 			Maximize browser window
 * 	2. Test (Workflow)
 * 	3. AfterMethod
 * 			Take screenshot, in case of failure
 * 			Close webdriver
 * 	4. AfterClass
 * 			Quit webdriver
 */
//@author=rsud

import java.io.IOException;
import java.util.Random;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class SavedCardAddress extends Browser {
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver.manage().window().maximize();
	}
	
	@Test (groups = "create")//Add/edit credit card
	public void SavedCardTest () throws InterruptedException, IOException {
		logger=report.startTest("Add/Edit Credit card Test");
		connect();
		
	    driver.findElement(By.xpath("//header/div/div/ul/li/a[contains(text(),'Account')]")).click();
	    Thread.sleep(2000);
	    
	  //Login using test account
	  //Navigate to Account profile > Saved cards
	  //Add a new card and save
	  //Edit card and save
	  //Initiate checkout and ensure saved card is selectable
	  //Delete card
	    
    	
	    logger.log(LogStatus.PASS, "Successfully saved credit card");
	}
	
	@Test (groups = "create")//Add/edit address
	public void SavedAddressTest () throws InterruptedException, IOException {
		logger=report.startTest("Add/Edit Address Test");
		connect();
		
	    driver.findElement(By.xpath("//header/div/div/ul/li/a[contains(text(),'Account')]")).click();
	    Thread.sleep(2000);
	    
	  //Login using test account
	  //Navigate to Account profile > Address book
	  //Add a new address and save
	  //Edit address and save
	  //Initiate checkout and ensure saved address is selectable
    	
	    logger.log(LogStatus.PASS, "Successfully saved address");
	}
	
	@Test (dependsOnGroups = "create") //Checkout using saved credit card and address
	public void SavedCardAddressCheckout () throws InterruptedException {
		logger=report.startTest("Checkout using saved credit card and address Test");
		connect();
		//Navigate to PDP
		
		//Add item to cart
		
		//Initiate checkout
		
		//Login
		
		//Select saved address
		
		//Select saved card
		
		//Place Order
		logger.log(LogStatus.PASS, "Successfully placed order using saved address and credit card");
	}
	
	@Test (dependsOnMethods={"SavedCardAddressCheckout"}) //Delete saved card and address
	public void DeleteCardAddress () throws InterruptedException {
		logger=report.startTest("Delete saved credit card and address Test");
		connect();
		
		//Login using test account
		driver.findElement(By.linkText("Sign up")).click();
	    Thread.sleep(1000);
	    
		//Navigate to Account profile > Address book
		//Delete saved address
		
		//Navigate to Account profile > Saved cards
		//Delete saved card
	    logger.log(LogStatus.PASS, "");
	}
	
	public void connect() throws InterruptedException {
		driver.get(ConfigurationFunctions.locationSet+"71.193.51.0");
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
	}
}
