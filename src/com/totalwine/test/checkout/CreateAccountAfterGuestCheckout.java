package com.totalwine.test.checkout;

/*
 *  Creating account after Guest Checkout flow
 *  Work flow : 
 *  1. Access �Change location�
 *  2. Click on Choose a shipping destination
 *  3. Choose a state from the drop down and click on �Make this my shipping state�
 *  4. Access PDP for item
 *  5. Add to cart
 *  6. View Cart
 *  7. Initial Guest Checkout
 *  8. Tab 1 Checkout ( Delivery Address)
 *  9. Tab 2 Checkout (Billing)
 *  10. Tab 3 Checkout (Review and Submit)
 *  11. Order Confirmation Page
 *  12. On the right middle , click on �Create Account�
 *  13. In the �Sign up now� window fillup email, password, confirm password, preferred store, and then click on save button. 
 *  14. Fill-up all required fields and click on register
 *  15. Verify welcome message (We're happy you've joined the Total Discovery program, ---) displays

 * Technical Modules:
 * 	1. DataProvider: Checkout test input parameters
 * 	2. BeforeMethod (Test Pre-requisites):
 * 			Invoke WebDriver
 * 			Maximize browser window
 * 	3. Test (Workflow)
 * 	4. AfterMethod
 * 			Take screenshot, in case of failure
 * 			Close WebDriver
 * 	5. AfterClass
 * 			Quit WebDriver
 */

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.testng.Assert;
import jxl.read.biff.BiffException;
import org.testng.*;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.Keys;

import com.relevantcodes.extentreports.LogStatus;
import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

import com.totalwine.test.pages.CheckoutPage;


public class CreateAccountAfterGuestCheckout extends Browser {

	@DataProvider(name="CheckoutParameters")
    public Object[][] createData() {
    	Object[][] retObjArr=ConfigurationFunctions.getTableArray(ConfigurationFunctions.resourcePath,"Checkout", "CreateAccountAfterGuestCheckoutBF");
        return(retObjArr);
    } 
	
	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();	
		 }  
	
	@Test (dataProvider = "CheckoutParameters")
	public void PostGuestCheckoutTestAccountCreation (String Location,String StoreName,String PDP,String Quantity,String ShipOption,String FirstName,
			String LastName,String Company,String Address1,String Address2,String City,String State,String Zip,String Email, 
			String Phone,String CreditCard,String ExpirationMonth,String ExpirationYear,String CVV)
					
					throws InterruptedException, BiffException, IOException {
		
		Random rand = new Random();
	    int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
	    int randomNum_2 = rand.nextInt((1000 - 1) + 1) + 1;
		
	    logger=report.startTest("Creating account after Guest Checkout");
	    
		driver.get(ConfigurationFunctions.locationSet+Location);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    
		Assert.assertEquals(StoreName, driver.findElement(By.cssSelector("span.store-details-store-name.flyover-src")).getText());
	    logger.log(LogStatus.PASS, "The site is configured for a Shipping order");
	    
	    ConfigurationFunctions.highlightElement(driver,driver.findElement(By.cssSelector("span.store-details-store-name.flyover-src")));
		
	    	    
	    // **  Selecting a product from PDP
		driver.get(ConfigurationFunctions.accessURL+PDP);
		Thread.sleep(3000);
				
		// **  Add to Cart
		String productId = driver.findElement(By.cssSelector("div.anProductId")).getText();
		System.out.println(productId);
		Thread.sleep(2000);
			    
	    driver.findElement(By.xpath("(//button[@id='"+productId+"'])[2]")).click(); //Clicking the ATC button
		Thread.sleep (3000);
		
	    driver.get(ConfigurationFunctions.accessURL+"/cart");
	    Thread.sleep(3000);
	    logger.log(LogStatus.PASS, "The test item is successfully added to cart");
	    
	    //  ** Shopping Cart
	    WebElement scroll = driver.findElement(By.id("checkout"));  
	    scroll.sendKeys(Keys.PAGE_DOWN); //  ** Scrolling down page
	    
	    driver.findElement(By.cssSelector("input[id='zipCode']")).click();
	    driver.findElement(By.cssSelector("input[id='zipCode']")).clear();
	    driver.findElement(By.cssSelector("input[id='zipCode']")).sendKeys(Zip);    
	    Thread.sleep(2000);
	    
	    //driver.findElement(By.cssSelector("input.anZipForm")).click();
	    driver.findElement(By.cssSelector("input.anZipForm[value='Submit']")).click();
	    Thread.sleep(6000);
	    	    
	    driver.findElement(By.cssSelector("#deliveryMode > div.customselect > span.itemval")).click();
	    driver.findElement(By.cssSelector("li[data-val="+ShipOption+"]")).click();
	    Thread.sleep(3000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div[class=\"width-100 totalDotBorder noBorder ship-cost\"]")).isEmpty(),false); //**Validate appearance of shipping cost
	    Assert.assertEquals(driver.findElements(By.cssSelector("input.anVoucherForm")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.name("qty")).isEmpty(),false);
	      
	    driver.findElement(By.id("checkout")).click();
	    Thread.sleep(3000);
  
	    //  **  Next Page (Login/Checkout as Guest)
	    driver.findElement(By.cssSelector("#checkoutGuestForm > div.button-container > button.btn.btn-red")).click();
	    Thread.sleep(3000);
	    logger.log(LogStatus.PASS, "Validate checkout as Guest/LoggedIn user");
	    
	    // **  Checkout Tab 1
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys(FirstName);
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys(LastName);
	    driver.findElement(By.id("companyName")).clear();
	    driver.findElement(By.id("companyName")).sendKeys(Company);
	    driver.findElement(By.id("addressLine1")).clear();
	    driver.findElement(By.id("addressLine1")).sendKeys(Address1);
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys(City);
	    

	    // *** Creating Random Email
	    driver.findElement(By.id("shipping-email")).clear();
	    driver.findElement(By.id("shipping-email")).sendKeys("automatedtester_"+randomNum+"."+randomNum_2+"@totalwine.com");
	    	String email = driver.findElement(By.id("shipping-email")).getAttribute("value");
	    	System.out.println("Registered Email Address: "+email);
	    	
	    driver.findElement(By.id("shipping-phoneNumber")).clear();
	    driver.findElement(By.id("shipping-phoneNumber")).sendKeys(Phone);
	    
	    driver.findElement(By.id("btnShipAuth1")).click();
	    Thread.sleep(5000);
	    logger.log(LogStatus.PASS, "Creating account after Guest Checkout Tab 1");
	    
	    
	    // ** Checkout Tab 2

//	    WebElement radioBtn = driver.findElement(By.xpath(".//*[@value='DISCOVER']"));  //** If paid by Discover card
//	    WebElement radioBtn = driver.findElement(By.cssSelector("input#custom_card_type[value='AMEX']"));  //** If paid by Amex card
//	    radioBtn.click();
	        
	    driver.findElement(By.id("ssl_account_data")).click();
	    driver.findElement(By.id("ssl_account_data")).clear();
	    driver.findElement(By.id("ssl_account_data")).sendKeys(CreditCard);
	    driver.findElement(By.cssSelector("div[class=\"inputHolder month\"]")).click();
	    driver.findElement(By.xpath("//td[2]/div/div/div/div/div/div/ul/li[2]")).click();
	    driver.findElement(By.cssSelector("div[class=\"inputHolder year\"]")).click();
	    driver.findElement(By.xpath("//td[2]/div[2]/div/div/div/div/div/ul/li[3]")).click();
	    driver.findElement(By.id("ssl_cvv2cvc2")).clear();
	    driver.findElement(By.id("ssl_cvv2cvc2")).sendKeys(CVV);
	    driver.findElement(By.id("ssl_first_name")).clear();
	    driver.findElement(By.id("ssl_first_name")).sendKeys(FirstName);
	    driver.findElement(By.id("ssl_last_name")).clear();
	    driver.findElement(By.id("ssl_last_name")).sendKeys(LastName);
	    driver.findElement(By.id("ssl_avs_address")).clear();
	    driver.findElement(By.id("ssl_avs_address")).sendKeys(Address1);
	    driver.findElement(By.id("ssl_company")).clear();
	    driver.findElement(By.id("ssl_company")).sendKeys(Company);
	    driver.findElement(By.id("ssl_address2")).clear();
	    driver.findElement(By.id("ssl_address2")).sendKeys(Address2);
	    driver.findElement(By.id("ssl_city")).clear();
	    driver.findElement(By.id("ssl_city")).sendKeys(City);
	    
	    driver.findElement(By.xpath("//table[@id='tblAddress']/tbody/tr[7]/td[2]/div/div/span")).click(); // ** Adding State from drop-down menu
	    driver.findElement(By.cssSelector("li[data-val=\""+State+"\"]")).click();
	    
	    driver.findElement(By.id("ssl_avs_zip")).clear();
	    driver.findElement(By.id("ssl_avs_zip")).sendKeys(Zip);
	    driver.findElement(By.name("process")).click();
	    Thread.sleep(5000);
	    logger.log(LogStatus.PASS, "Creating account after Guest Checkout Tab 2");
	      
	    
	    // **  Checkout Tab 3
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.review-tab")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw co-rvw-instore\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw co-rvw-pymnt\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[data-attr=\"itemPrice_1\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[data-attr=\"itemPrice_2\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[class=\"price-text item-total anTax\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[class=\"price-text item-total co-pr-item-total\"]")).isEmpty(),false);
	    
	    
	    driver.findElement(By.id("check_box_age")).click();
	    driver.findElement(By.cssSelector("button.btn-red.btn-place-order.anPlaceOrder")).click();
	    Thread.sleep(3000);
	    logger.log(LogStatus.PASS, "Creating account after Guest Checkout Tab 3");
	    
	    //  ** Order Confirmation
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.co-conf-thank-text")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div")).isEmpty(),false);
	    logger.log(LogStatus.PASS, "Creating account after Guest Checkout order confirmation");

  
	    
	    //  ** Creating Account
	    driver.findElement(By.id("btnCreateAcc")).sendKeys(Keys.ARROW_DOWN);
	    driver.findElement(By.id("btnCreateAcc")).click();
	    
	    
	    //  ** Checking for survey Popup
	    if (driver.findElements(By.xpath("//img[contains(@src,'https://qdistribution.qualtrics.com/WRQualtricsShared/Graphics//black_popup_x.png')]")).size()!=0)
	    	driver.findElement(By.xpath("//img[contains(@src,'https://qdistribution.qualtrics.com/WRQualtricsShared/Graphics//black_popup_x.png')]")).click();
	    Thread.sleep(10000);

	    driver.findElement(By.xpath(".//*[@id='co-pass']")).sendKeys("grapes123");
	    driver.findElement(By.xpath(".//*[@id='co-pass-re']")).sendKeys("grapes123");

	    
	    
	    //Check for survey pop-up
	    if (driver.findElements(By.xpath("//img[contains(@src,'https://qdistribution.qualtrics.com/WRQualtricsShared/Graphics//black_popup_x.png')]")).size()!=0)
	    	driver.findElement(By.xpath("//img[contains(@src,'https://qdistribution.qualtrics.com/WRQualtricsShared/Graphics//black_popup_x.png')]")).click();
	    Thread.sleep(2000);
	    
	    
	    WebElement element = driver.findElement(By.xpath(".//*[@id='frmCOCreateAcc']/table/tbody/tr[5]/td[2]/div/div/div/span/span"));  
        	new Actions(driver).moveToElement(element).perform();   //  ** Selecting element from drop-down
        	element.click();
	    Thread.sleep(2000);

	    driver.findElement(By.xpath(".//*[@id='frmCOCreateAcc']/table/tbody/tr[5]/td[2]/div/div/div/div/div/div[1]/ul/li[3]")).click();
	    Thread.sleep(2000);

	    driver.findElement(By.id("check_box_100")).click();
	    driver.findElement(By.id("check_box_101")).click();
	    driver.findElement(By.id("btnCOSaveAuth")).click();
	    logger.log(LogStatus.PASS, "Creating account after Guest Checkout - creating account");


	    //  ** Creating online profile
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys(FirstName);
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys(LastName);
	    driver.findElement(By.id("phone")).sendKeys(Phone);
	    driver.findElement(By.id("compName")).clear();
	    driver.findElement(By.id("compName")).sendKeys("Total Wine & More");
	    driver.findElement(By.id("address1")).clear();
	    driver.findElement(By.id("address1")).sendKeys("6600 Rockledge Dr.");
	    driver.findElement(By.id("address2")).clear();
	    driver.findElement(By.id("address2")).sendKeys("Suite 210");
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("Bethesda");
	    
	    
	    
	    WebElement scroll1 = driver.findElement(By.id("zipCode"));
	 	scroll1.sendKeys(Keys.ARROW_DOWN);   //** Scrolling down the page upto a specific element 
	    
	    WebElement element2 = driver.findElement(By.xpath(".//*[@id='laneuserregistration']/section[2]/div[2]/div/div[10]/div/div/span/span"));  
        	new Actions(driver).moveToElement(element2).perform();
        	element2.click();   
	    Thread.sleep(1000);
	    driver.findElement(By.xpath(".//*[@id='laneuserregistration']/section[2]/div[2]/div/div[10]/div/div/div/div/div[1]/ul/li[3]")).click();
	    
	    driver.findElement(By.id("zipCode")).clear();
	    driver.findElement(By.id("zipCode")).sendKeys("20817");
	    logger.log(LogStatus.PASS, "Preferred Store Indication");
	    
	    WebElement scroll2 = driver.findElement(By.cssSelector("input[name=ageCheck]"));
	 	scroll2.sendKeys(Keys.ARROW_DOWN);   //** Scrolling down the page upto a specific element 
	    
	    driver.findElement(By.cssSelector("input[name=ageCheck]")).click();
	    driver.findElement(By.cssSelector("input[name=termsAndCondCheck]")).click();
	    driver.findElement(By.id("btnlusrregisteration")).click();
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
	    
	    logger.log(LogStatus.PASS, "Registration Completed");
	   
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
	    logger.log(LogStatus.PASS, "Online Profile and Account Home verified");

	}
	}