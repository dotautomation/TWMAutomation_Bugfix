package com.totalwine.test.aml;

/*
 * Web Account Registration Workflow
 * Workflow:
 * 	1. Click the "Account" link in top nav
 * 	2. Assert the presence of all links in the popup screen
 * 	3. Click the "Sign Up" link and navigate to the Registration screen
 * 	4. Complete the form with registration information and submit the form
 * 	5. Validate the presence of elements on the registration confirmation screen
 * 	6. Complete preferences and save the information
 *  7. Validate the elements presented on the Account home page.
 *  8. Log out and attempt to re-login using the same credentials
 *  9. Validate the logged in state ensuring the credentials work.
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

public class WebAccountRegistration extends Browser {
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver.manage().window().maximize();
	}
	
	@Test 
	public void RegistrationTest () throws InterruptedException, BiffException, IOException {
		logger=report.startTest("Web Registration Test");
		Random rand = new Random();
	    int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
	    int randomNum_2 = rand.nextInt((1000 - 1) + 1) + 1;
		StringBuffer errorBuffer = new StringBuffer();
		//ConfigurationFunctions.initialStartUp("71.193.51.0");
		
		//driver.manage().window().maximize();
		driver.get(ConfigurationFunctions.locationSet+"71.193.51.0");
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
		
	    driver.findElement(By.xpath("//header/div/div/ul/li/a[contains(text(),'Account')]")).click();
	    Thread.sleep(2000);
	    
    	Assert.assertEquals(driver.findElements(By.linkText("Sign into your account")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Account profile")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Online order history")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Order status")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Shopping list")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.pgmtxt")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Learn more")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.signinup-items")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Options within the Account modal");
	    driver.findElement(By.linkText("Sign up")).click();
	    Thread.sleep(1000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.aml-heading")).isEmpty(),false);
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys("Automated");
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys("Tester");
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("automatedtester_"+randomNum+"."+randomNum_2+"@totalwine.com");
	    	String email = driver.findElement(By.id("email")).getAttribute("value");
	    	System.out.println("Registered Email Address: "+email);
	    driver.findElement(By.id("checkEmail")).clear();
	    driver.findElement(By.id("checkEmail")).sendKeys(email);
	    driver.findElement(By.id("pwd")).sendKeys("grapes");
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.passwordstrength > ul > li.active")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Password strength indicator");
	    driver.findElement(By.id("pwd")).sendKeys("grapes123");
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='newuserregistration']/section/div[2]/div[5]/div[11]/div/ul/li[3]")).isEmpty(),false);
	    driver.findElement(By.id("pwd")).clear();
	    driver.findElement(By.id("pwd")).sendKeys("");
	    driver.findElement(By.id("pwd")).sendKeys("grapes123!");
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='newuserregistration']/section/div[2]/div[5]/div[11]/div/ul/li[4]")).isEmpty(),false);
	    driver.findElement(By.id("checkPwd")).sendKeys("grapes123!");
	    driver.findElement(By.id("phone")).sendKeys("3015470004");
	    Assert.assertEquals("301-547-0004", driver.findElement(By.id("phone")).getAttribute("value"));
	    driver.findElement(By.id("compName")).clear();
	    driver.findElement(By.id("compName")).sendKeys("Total Wine & More");
	    driver.findElement(By.id("address1")).clear();
	    driver.findElement(By.id("address1")).sendKeys("6600 Rockledge Dr.");
	    driver.findElement(By.id("address2")).clear();
	    driver.findElement(By.id("address2")).sendKeys("Suite 210");
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("Bethesda");
	    
	    WebElement element = driver.findElement(By.xpath("//div[10]/div/div/span/span"));  
        new Actions(driver).moveToElement(element).perform();  
        element.click();
	    
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//div[10]/div/div/div/div/div/ul/li[2]")).click();
	    driver.findElement(By.id("zipCode")).clear();
	    driver.findElement(By.id("zipCode")).sendKeys("20817");
	    Assert.assertEquals(driver.findElements(By.cssSelector("input[name=preferredStoreDefault]")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Preferred Store Indication");
	    WebElement scroll = driver.findElement(By.cssSelector("input[name=ageCheck]"));
	 	scroll.sendKeys(Keys.ARROW_DOWN);
	    
	    driver.findElement(By.cssSelector("input[name=ageCheck]")).click();
	    driver.findElement(By.cssSelector("input[name=termsAndCondCheck]")).click();
	    driver.findElement(By.id("btnnuregisteration")).click();
	    Thread.sleep(10000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span.benefits-desc")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='registerConfForm']/main/section[2]/section/section/div[2]/div[3]/ul/li[2]/a/span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='registerConfForm']/main/section[2]/section/section/div[2]/div[3]/ul/li[3]/a/span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='registerConfForm']/main/section[2]/section/section/div[2]/div[3]/ul/li[4]/a/span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Start shopping")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("c0010")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("c0020")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("c0030")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("c0050")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("em.icon.birthday")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("em.icon.mobilephone")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Wine/Beer/Spirits/Accessories Preferences");
	    scroll = driver.findElement(By.id("btnSaveAccount"));
	    scroll.sendKeys(Keys.ARROW_DOWN);
	    driver.findElement(By.id("btnSaveAccount")).click();
	    driver.findElement(By.cssSelector("div.ahp-heading")).click();
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.ahp-heading")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Your account")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Orders")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Your shopping lists")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span.rewards-title")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("a[class=analyticsUpdateAcc]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Online order history")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("a[class=analyticsPrefStore]")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Left Navigation on Account home page");
	    //Logout and attempt to re-login using registration information
	    driver.findElement(By.linkText("Welcome, Automated")).click();
	    driver.findElement(By.linkText("Log out")).click();
	    logger.log(LogStatus.PASS, "Successful logout");
	}
}
