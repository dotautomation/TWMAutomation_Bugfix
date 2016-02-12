package com.totalwine.test.agegating;

/*
 * Age Gate Workflow
 * Workflow:
 * 	1. Access the site
 * 	2. On the Age Gate, click No
 * 	3. Validate the presence of the splash screen containing the 21+ notification
 *  4. Validate that http://responsibility.org/ loads
 *  5. Re-access the site
 *  6. On the Age Gate, click Yes
 *  7. Validate that the home page loads
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
import org.testng.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.pages.PageGlobal;
import com.totalwine.test.pages.PageHomepage;
import com.totalwine.test.trials.Browser;

public class AgeGate extends Browser {
	
	private String IP="71.193.51.0";
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver.manage().window().maximize();
	}
	
	@Test
	public void AgeGateTest () throws InterruptedException {
		logger=report.startTest("Age Gate Test");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(PageGlobal.AgeGateNo).click();
		Thread.sleep(1000);
		//Splash screen validation
		Assert.assertEquals(driver.findElements(PageGlobal.AgeGateNoError).isEmpty(),false);
		logger.log(LogStatus.PASS, "Screen notification upon clicking No");
		//Validate URL for responsibility.org
		Thread.sleep(10000);
		String url = driver.getCurrentUrl();
		System.out.println(url);
		Assert.assertEquals(url, "http://responsibility.org/");
		logger.log(LogStatus.PASS, "Clicking No on the Age Gate redirects customer to responsibility.org");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(PageGlobal.AgeGateYes).click();
		Thread.sleep(5000);
	    driver.findElement(PageGlobal.NewSiteIntroClose).click();
	    Thread.sleep(5000);
	    Assert.assertEquals(driver.findElements(PageHomepage.HomepageCarousel).isEmpty(),false); //HomePage validation
	    logger.log(LogStatus.PASS, "Clicking Yes on the Age Gate directs customer to Home page");
	}
}
