package com.totalwine.test.global;

/*
 */

import java.io.File;
import java.io.IOException;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.totalwine.test.config.ConfigurationFunctions;

public class SimpleBrowse  {
	
	private WebDriver driver;

	@BeforeClass
	  public void setUp() throws Exception {
		File file = new File("C:/totalwine/Library/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    Thread.sleep(2000);
	    driver.get(ConfigurationFunctions.locationSet+"69.67.93.0");
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
	  }  
	
	@Test (invocationCount=10)
	public void SimpleBrowseTest () throws InterruptedException, BiffException, IOException {
		
		driver.findElement(By.linkText("Account")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.cssSelector("a.acc-link.analyticsSignIn")).click();
	    Thread.sleep(2000);
	    driver.switchTo().frame("iframe-signin-overlay");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("rsud@totalwine.com");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("grapes123");
	    driver.findElement(By.xpath("//button[@type='button']")).click();
	    Thread.sleep(6000);
	    		
		Actions action=new Actions(driver);
		if (driver.findElement(By.cssSelector("button.btn.btn-brown.cartMergeBtn")).isDisplayed())
			driver.findElement(By.cssSelector("button.btn.btn-brown.cartMergeBtn")).click();
		
		
		//Wine
		WebElement toplevelnav = driver.findElement(By.xpath("//a[contains(@href,'/c/c0020')]")); 
		action.moveToElement(toplevelnav).build().perform(); //Top Level Menu Hover
		WebElement plpnav=driver.findElement(By.xpath("//a[contains(@href,'/c/000002')]"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", plpnav);
		Thread.sleep(5000);
		//Access the PDP
		driver.findElement(By.cssSelector("a.analyticsProductName")).click(); //Click the first item link in the PLP
		Thread.sleep(6500);
		//driver.findElement(By.cssSelector("section.pdp-wrapper > section > div.pdp-buy > form > div.buy-prod > button.btn.btn-red.mini-cart-popup.anAddToCart")).click();
		//driver.findElement(By.xpath("(//button[@id='2403750-1'])[3]")).click();
		String productID = driver.findElement(By.cssSelector("div.anProductId")).getText();
		driver.findElement(By.xpath("(//button[@id='"+productID+"'])[3]")).click();
		Thread.sleep(5000);
		
		//Beer
		toplevelnav = driver.findElement(By.xpath("//a[contains(@href,'/c/c0010')]")); 
		action.moveToElement(toplevelnav).build().perform(); //Top Level Menu Hover
		plpnav=driver.findElement(By.xpath("//a[contains(@href,'/c/001202')]"));
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", plpnav);
		Thread.sleep(5000);
		//Access the PDP
		driver.findElement(By.cssSelector("a.analyticsProductName")).click(); //Click the first item link in the PLP
		Thread.sleep(5000);
		productID = driver.findElement(By.cssSelector("div.anProductId")).getText();
		driver.findElement(By.xpath("(//button[@id='"+productID+"'])[3]")).click();
		//driver.findElement(By.cssSelector("em.icon-plus")).click();
		Thread.sleep(5000);
		
		//Spirit
		toplevelnav = driver.findElement(By.xpath("//a[contains(@href,'/c/c0030')]")); 
		action.moveToElement(toplevelnav).build().perform(); //Top Level Menu Hover
		plpnav=driver.findElement(By.xpath("//a[contains(@href,'/c/000866')]"));
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", plpnav);
		Thread.sleep(5000);
		//Access the PDP
		driver.findElement(By.cssSelector("a.analyticsProductName")).click(); //Click the first item link in the PLP
		Thread.sleep(5000);
		productID = driver.findElement(By.cssSelector("div.anProductId")).getText();
		driver.findElement(By.xpath("(//button[@id='"+productID+"'])[3]")).click();
		Thread.sleep(5000);
		
		//Logout
		driver.findElement(By.linkText("Welcome, Rajat")).click();
	    driver.findElement(By.linkText("Log out")).click();
		
	}
	
	@AfterClass 
	public void takeScreenShotOnFailure() throws IOException {
		driver.close();
	}
}
