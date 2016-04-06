package com.totalwine.test.backoffice;

/*
 * C3 Sanity Workflow
 * Workflow:
 * 1. Access the CS Cockpit
 * 2. Login using valid credentials
 * 3. Validate the menus displayed upon a successful login
 * 4. Find a customer and access it's record
 * 5. Validate the customer record
 * 6. Find an order and access it's record
 * 7. Validate the order record
 * 8. Log out
 *		
 * Technical Modules:
 * 	1. BeforeMethod (Test Pre-requisites):
 * 			Maximize browser window
 * 	2. Test (Workflow)
 * 	3. AfterMethod
 * 			Take screenshot
 * 			Close webdriver
 */

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;
import com.totalwine.test.trials.ParallelBrowser;

public class C3Validation extends ParallelBrowser {
	
	@BeforeMethod
	public void setUp() throws Exception {
	    driver.manage().window().maximize();
	  }  
	
/*	@DataProvider(name="CSParameters")
    public Object[][] createData() {
    	Object[][] retObjArr=ConfigurationFunctions.getTableArray(ConfigurationFunctions.resourcePath,"CS", "CSParameters");
        return(retObjArr);
    }*/ 
	
	@Test (invocationCount=40/*,dataProvider = "CSParameters"*/)
	public void C3LoginTest () throws InterruptedException {
		logger=report.startTest("CS Cockpit Login Test");
		String[] customers = {"Abe","Ahm","Aim","Al","Alv","Alv","Rajat","Anu","Anupriya","Ara","ari","Arm","Raj","Auto","Aut","Automated","Bha","Bla",
				"Bra","Bre","Bry","Car","charan","charan","Checkout","Chr","Chris","Cla","Cli","na","COR","COU","Cyn","DAN","Dar","Daw",
				"Derek","Check","dol","don","DOT","Dou","dou","Ra","Raja","Dus","Dwarak","dwrak","out","Edd"};
		String[] orders = {"28995183","28996282","28996287","28997003","28997006","28997008","28997021","28997023","28998004","28998010","28998012","28998017",
				"28998025","28999002","29000001","29004015","29005003","29006002","29006007","29006018","29006028","29006034","29006034","29006056","29006100",
				"29006105","29006107","29006113","29006115","29007035","29007051","29007055","29008012","29008016","29009009","29009013","29010005","29010007",
				"29010016","29010021","29010024","29010026","29010029","29010031","29011006","29011019","29011022","29011025","29011028","29011032"};
		driver.get("http://bugfix.totalwine.com/cscockpit/login.zul");
		Thread.sleep(5000);
		C3Login();
		
		//Validate Links in left menu
		Assert.assertEquals(driver.findElements(By.linkText("Find Customer")).isEmpty(), false);
		Assert.assertEquals(driver.findElements(By.linkText("New Customer")).isEmpty(), false);
		Assert.assertEquals(driver.findElements(By.linkText("Email Signup")).isEmpty(), false);
		Assert.assertEquals(driver.findElements(By.linkText("Find Order")).isEmpty(), false);
		Assert.assertEquals(driver.findElements(By.linkText("Find Ticket")).isEmpty(), false);
		Assert.assertEquals(driver.findElements(By.linkText("New Ticket")).isEmpty(), false);
		System.out.println("CS Cockpit Left Menu contents validated");
		System.out.println("Successfully logged in");
		
		for (int count = 0;count<50;count++) {
			
			//Find a customer
			if (driver.findElements(By.linkText("Find Customer")).size()!=0)
				driver.findElement(By.linkText("Find Customer")).click();
			else {
				driver.findElement(By.linkText("Change Customer")).click();
			}
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[contains(@class, 'csSearchPane')]/input[1]")).clear();
			driver.findElement(By.xpath("//div[contains(@class, 'csSearchPane')]/input[1]")).sendKeys(customers[count]);
			driver.findElement(By.xpath("//td[text()[contains(.,'Search')]]")).click();
			Thread.sleep(2000);
			while (driver.findElements(By.cssSelector("div.z-loading-indicator")).size()!=0)
				Thread.sleep(2000);
			System.out.println("Customer search validated: "+customers[count]);
			
			//Validate Customer search results
			driver.findElement(By.xpath("//td[text()[contains(.,'Select')]]")).click();
			Thread.sleep(2000);
			if (driver.findElements(By.xpath("//td[text()[contains(.,'Yes')]]")).size()!=0) {
				driver.findElement(By.xpath("//td[text()[contains(.,'Yes')]]")).click();
			}
			new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.csCustomerEditWidgetFrame")));
			System.out.println("Customer record validated");
			
			//Find an order
			driver.findElement(By.linkText("Find Order")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[contains(@class, 'csSearchPane')]/input[1]")).clear();
			driver.findElement(By.xpath("//div[contains(@class, 'csSearchPane')]/input[1]")).sendKeys(orders[count]);
			driver.findElement(By.xpath("//td[text()[contains(.,'Search')]]")).click();
			Thread.sleep(2000);
			while (driver.findElements(By.cssSelector("div.z-loading-indicator")).size()!=0)
				Thread.sleep(2000);
			System.out.println("Order search validated");
			
			//Validate Order search results
			driver.findElement(By.xpath("//td[contains(@class, 'z-button-cm') and text() = 'Select']")).click();
			Thread.sleep(3000);
			//Handling Active Customer popup (new with 3/9/2016 hotfix)
			driver.findElement(By.xpath("//td[text()[contains(.,'Yes')]]")).click();
			Thread.sleep(2000);
			new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[text()[contains(.,'AUTHORIZATION')]]"))));
			System.out.println("Order record validated: "+orders[count]);
		}
		
		
		//Logout
		driver.findElement(By.xpath("//span[text()[contains(.,'Menu')]]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()[contains(.,'Logout')]]")).click();
		System.out.println("Sucessfully logged out");
	}
	
	public void C3Login() throws InterruptedException {
		//C3 Login
		driver.findElement(By.cssSelector("input[name=j_username]")).clear();
		driver.findElement(By.cssSelector("input[name=j_username]")).sendKeys("rsud");;
		driver.findElement(By.cssSelector("input[name=j_password]")).clear();
		driver.findElement(By.cssSelector("input[name=j_password]")).sendKeys(ConfigurationFunctions.TESTPWD);
		driver.findElement(By.cssSelector("td.z-button-cm")).click();
		//Thread.sleep(10000);
		new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Find Customer")));
	}
}
