package com.totalwine.test.productlist;

/*
 * PLP Filter Workflow
 * Workflow:
 * 	1. Access the PLP for Wine > White Wine
 * 	2. Select the facet filters and validate the following elements associated with each selection: 
 * 	  a. Price Range: Validate that the price on the top PLP tile is within the price range selection
 *    b. Rating Source: Validate that the first PLP tile depicts the rating source selected
 *    c. Rating Range: Validate that the rating badge is present on the first PLP tile
 *    d. Country: Validate that the country selected is displayed as an attribute on the first PLP tile
 *    e. Appelation: Validate that the selected appelation is listed on the first PLP tile
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
import java.awt.AWTException;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.totalwine.test.actions.SiteAccess;
import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class PLPGridView extends Browser {

	private String IP="71.193.51.0";

	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();
	}  
	
	@Test 
	public void PLPGridTest () throws InterruptedException, BiffException, IOException, AWTException {
		logger=report.startTest("PLP Grid Test");
		SiteAccess.ActionAccessSite(driver, IP);
	    
	    //Navigate to a PLP
	    Actions action=new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor)driver;
	    WebElement wineNav = driver.findElement(By.xpath("//a[contains(@href,'/c/c0020')]")); 
		action.moveToElement(wineNav).build().perform(); //Top Level Menu Hover
		WebElement winePLPNav=driver.findElement(By.xpath("//a[contains(@href,'/wine/red-wine/c/')]"));
		js.executeScript("arguments[0].click();", winePLPNav);
		Thread.sleep(5000);
		WebElement wineMove = driver.findElement(By.cssSelector("ul.header-classes")); //Moving the mouse away from the top level menu 
		action.moveToElement(wineMove).build().perform(); 
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Navigate to PLP");
	    
		//Apply a facet on the default list view
		driver.findElement(By.linkText("Wine Varietal & Type")).click();
		driver.findElement(By.id("check_box_showmoreCabernet Sauvignonvarietaltype")).click(); //Cabernet Sauvignon facet
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.inner-items-wrapper > ul > li > a.filter-link > span.filter-value")).getText(), "Cabernet Sauvignon","Filter Value incorrect");
		logger.log(LogStatus.PASS, "Apply the varietal facet");
		//Access the Grid View
		driver.findElement(By.cssSelector("a.show-grid.analyticsViewAsGrid")).click();
		Thread.sleep(3000);
		
	    //Validate that the previously applied facet persists
		Assert.assertEquals(driver.findElement(By.cssSelector("div.inner-items-wrapper > ul > li > a.filter-link > span.filter-value")).getText(), "Cabernet Sauvignon","Previous facet setting isn't retained");
		logger.log(LogStatus.PASS, "The varietal facet persists when the Grid view is switched to");
		
	    //Validate presence of elements (Store, Item Size, Price, Badges, Expert Rating, Customer Rating, Customer Reviews, ATC, ATL
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-list-img-wdlogo")).isEmpty(), false,"WD logo doesn't appear correctly"); //WD Logo
		Assert.assertEquals(driver.findElements(By.cssSelector("h2.plp-product-title")).isEmpty(), false,"Product title doesn't appear correctly"); //Title
		Assert.assertEquals(driver.findElement(By.cssSelector("div.plp-product-qty")).getText(), "750ml","Product volume/size doesn't appear correctly"); //Item Size
		Assert.assertEquals(driver.findElements(By.cssSelector("div.product-img-div")).isEmpty(), false,"Product image doesn't appear correctly"); //Product image
		Assert.assertEquals(driver.findElements(By.cssSelector("div.wine-spectator-div")).isEmpty(), false,"Expert rating doesn't appear correctly"); //Expert Rating 
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-list-img-staff-bot-bust")).isEmpty(), false,"Merchandising badge doesn't appear correctly"); //Merchandising badge
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-product-buy-limited")).isEmpty(), false,"Ltd. Qty. doesn't appear correctly"); //Ltd Qty.
		
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-product-buy-price-mix")).isEmpty(), false,"Mix6 price doesn't appear correctly"); //Mix6 price
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-product-buy-actual-price")).isEmpty(), false,"EDLP doesn't appear correctly"); //EDLP
		
		Assert.assertEquals(driver.findElements(By.cssSelector("div.pdpRatingStars")).isEmpty(), false,"Rating (stars) doesn't appear correctly"); //Rating (stars) 
		Assert.assertEquals(driver.findElements(By.cssSelector("a.analyticsProductReviews")).isEmpty(), false,"No. of reviews doesn't appear correctly"); //No. of reviews
		
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-product-shipping")).isEmpty(), false,"Shipping badge doesn't appear correctly"); //Shipping badge
		Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-product-delivery")).isEmpty(), false,"Pickup badge doesn't appear correctly"); //Pickup badge
		Assert.assertEquals(driver.findElements(By.cssSelector("form.add_to_cart_form.clear_fix")).isEmpty(), false,"ATC button doesn't appear correctly"); // Add to cart
		Assert.assertEquals(driver.findElements(By.cssSelector("button.btn.btn-brown-pattern.anAddToListInit")).isEmpty(), false,"ATL button doesn't appear correctly"); //Add to list
		
	    //Validate absence of elements (Product code, Product attributes, Expert review)
		Assert.assertEquals(driver.findElement(By.cssSelector("div.plp-product-code")).getText(), "","Product code is not invisible"); //Product Code
		logger.log(LogStatus.PASS, "Grid View page elements");
		
	    //Sort Grid PLP
	    driver.findElement(By.cssSelector("div.dropdown.plp-product-sorting-sortby-dropdown > div > span.itemval")).click(); //Sort dropdown
	    Thread.sleep(2000);
	    driver.findElement(By.cssSelector("li[data-val=our-favorites]")).click(); //Our favorites
	    Thread.sleep(5000);
		Assert.assertEquals(driver.findElements(By.cssSelector("section.plp-product-content.grid")).isEmpty(),false,"Product grid doesn't appear correctly");
		
	    //Validate grid view is retained during pagination
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.cssSelector("a.pager-next.analyticsPageView")));
		
		//driver.findElement(By.cssSelector("a.pager-next.analyticsPageView")).sendKeys(Keys.PAGE_DOWN);
	    driver.findElement(By.cssSelector("a.pager-next.analyticsPageView")).click(); //Next page
	    Thread.sleep(3000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.plp-product-content.grid")).isEmpty(),false,"The grid doesn't appear correctly on the next page");
	    sAssert.assertAll();
	    logger.log(LogStatus.PASS, "Item sort in grid view");
	}
}
