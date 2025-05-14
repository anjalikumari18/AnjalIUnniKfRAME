package vtigerUsingTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.NewOrgPage;
import objectRepository.OrgInfoPage;
import objectRepository.OrganizationPage;
@Listeners(listenerUtility.ListenerImplementationClass.class)
public class CreateOrganizationUsingTestNGTest extends BaseClass{
    @Test(groups = "Smoke Test")
	public void createOrganizationTest() throws IOException {
		
		ExcelFileUtility eutil= new ExcelFileUtility();
		JavaUtility jutil= new JavaUtility();
		WebDriverUtility wutil= new WebDriverUtility();
		
		//Generate Random Number
		int randomNum = jutil.getRandomNumber();
		
		//Read the Test script data from excel file
		String orgName = eutil.readDataFromExcelFile("Org", 1, 2)+randomNum;
		
		
		wutil.waitForPageToLoad(driver);
		
		//Navigate to organization module
		HomePage hp = new HomePage(driver);
		hp.getOrglink().click();
		
		
		//Click on "Create Org" lookup image
		OrganizationPage op= new OrganizationPage(driver);
		op.getNewOrg().click();
		
		//Create org with mandatory details
		NewOrgPage nop= new NewOrgPage(driver);
		nop.createOrg(orgName);
	
		
		//Verification
		OrgInfoPage oip= new OrgInfoPage(driver);
		String headerInfo= oip.getHeaderText().getText();
		boolean actStatus = headerInfo.contains(orgName);
		//hard assert
		Assert.assertEquals(actStatus, true, "Failed");
		
		Reporter.log(orgName+" created successfully", true);
		
		//soft assert
		
//		SoftAssert soft= new SoftAssert();
//		soft.assertEquals(actStatus, true, "Failed");
//		Reporter.log(orgName+" created successfully", true);
//		soft.assertAll();
	
	}

}
