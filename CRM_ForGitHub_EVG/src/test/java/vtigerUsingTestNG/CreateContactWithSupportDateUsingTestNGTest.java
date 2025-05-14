package vtigerUsingTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import baseUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.ContactInfoPage;
import objectRepository.ContactPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.NewContactPage;
@Listeners(listenerUtility.ListenerImplementationClass.class)
public class CreateContactWithSupportDateUsingTestNGTest extends BaseClass{
    @Test(groups = "Regresstion Test")
	public void createContactWithSupportDateTest() throws EncryptedDocumentException, IOException {
		
		ExcelFileUtility eutil= new ExcelFileUtility();
		JavaUtility jutil= new JavaUtility();
		WebDriverUtility wutil= new WebDriverUtility();
		
				///Generate Random Number
		int randomNum = jutil.getRandomNumber();
		
		//Read the Test script data from excel file
		String lastName = eutil.readDataFromExcelFile("Contact", 1, 2)+randomNum;
		
		wutil.waitForPageToLoad(driver);
		
		//Navigate to Contact module
		HomePage hp= new HomePage(driver);
		hp.getContactlink().click();
				
		//Click on "Create contact" lookup image
		ContactPage cp= new ContactPage(driver);
		cp.getNewContact().click();
		
		//Create Contact with mandatory details
		NewContactPage ncp= new NewContactPage(driver);
		ncp.getLastname().sendKeys(lastName);
		
		 //Add the support date
		String startDate= jutil.getSystemDateyyyMMdd();
		String endDate= jutil.getRequiredDateyyyMMdd(30);
		ncp.getSupportSystemDate(startDate, endDate);
		ncp.getSave().click();
		
		//Verification
		ContactInfoPage cip= new ContactInfoPage(driver);
		String headerInfo = cip.getHeaderMsg().getText();
		if(headerInfo.contains(lastName)) {
			Reporter.log(lastName+" created successfully", true);
		}else {
			Reporter.log("Failed to create "+lastName, true);
		}
	
	}

}
