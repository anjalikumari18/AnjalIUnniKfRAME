package vtigerUsingTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.CampaignInfoPage;
import objectRepository.CampaignPage;
import objectRepository.CreateCampaignPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;
@Listeners(listenerUtility.ListenerImplementationClass.class)
public class CreateCampaignWithCloseDateUsingTestNGTest extends BaseClass{
     @Test(groups = "Regresstion Test")
	public void createCampaignTest() throws IOException{
		
		ExcelFileUtility eutil= new ExcelFileUtility();
		JavaUtility jutil= new JavaUtility();
		WebDriverUtility wutil= new WebDriverUtility();
		//Generate Random Number
				int randomNum = jutil.getRandomNumber();
				
				//Read the Test script data from excel file
				String campName = eutil.readDataFromExcelFile("Campaign", 1, 2)+randomNum;
		
		
		//Enter the URL
	 wutil.waitForPageToLoad(driver);
				
		//Navigate to more option
		HomePage hp = new HomePage(driver);
		hp.navigateToCampaign();
		
		//Click on the "Create new campaign" look up image
		CampaignPage cmp= new CampaignPage(driver);
		cmp.getCreateCampaign().click();
		
		//Enter the mandatory details
		CreateCampaignPage ccp= new CreateCampaignPage(driver);
		ccp.getCampName().sendKeys(campName);
		String closeDate =jutil.getRequiredDateyyyMMdd(5);
		ccp.getCloseDate().clear();
		ccp.getCloseDate().sendKeys(closeDate);
		ccp.getSaveBtn().click();
        
		//Verification
		CampaignInfoPage cmip= new CampaignInfoPage(driver);
		String headerInfo2 = cmip.getHeaderInfo().getText();
		if(headerInfo2.contains(campName)) {
			Reporter.log(campName+" created successfully", true);
		}else {
			Reporter.log("Failed to create "+campName, true);
		
		}
	
	}

}
