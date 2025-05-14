package vtigerUsingTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

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
import objectRepository.NewOrgPage;
import objectRepository.OrgInfoPage;
import objectRepository.OrganizationPage;
@Listeners(listenerUtility.ListenerImplementationClass.class)
public class CreateContactWithOrganizationUsingTestNGTest extends BaseClass{
    @Test(groups = "Regresstion Test")
	public void createContactWithOrganizationTest() throws EncryptedDocumentException, IOException {
		
		ExcelFileUtility eutil= new ExcelFileUtility();
		JavaUtility jutil= new JavaUtility();
		WebDriverUtility wutil= new WebDriverUtility();
		
				//Generate Random Number
				int randomNum = jutil.getRandomNumber();
				
				//Read the Test script data from excel file
				String lastName = eutil.readDataFromExcelFile("Contact", 1, 2)+randomNum;
				String orgName= eutil.readDataFromExcelFile("Contact", 1, 3)+randomNum;
	
				
				wutil.waitForPageToLoad(driver);
				
				//Navigate to organization module
				HomePage hp= new HomePage(driver);
				hp.getOrglink().click();
							
				//Click on "Create Org" lookup image
				OrganizationPage op= new OrganizationPage(driver);
				op.getNewOrg().click();
				
				//Create org with mandatory details
				NewOrgPage nop= new NewOrgPage(driver);
				nop.createOrg(orgName);
				
				//Verification
				OrgInfoPage oip= new OrgInfoPage(driver);
				String headerInfo = oip.getHeaderText().getText();
				if(headerInfo.contains(orgName)) {
					Reporter.log(orgName+" created successfully", true);
				}else {
					Reporter.log("Failed to create "+orgName, true);
				}
				
				//Navigate to Contact module
				hp.getContactlink().click();
				
				//Click on "Create contact" lookup image
				ContactPage cp= new ContactPage(driver);
				cp.getNewContact().click();
				
				//Create Contact with mandatory details
				NewContactPage ncp= new NewContactPage(driver);
				ncp.getLastname().sendKeys(lastName);
				ncp.addContactWithOrg(orgName);
				ncp.getSave().click();
				
				//Verification
				ContactInfoPage cip= new ContactInfoPage(driver);
				String headerInfo2 = cip.getHeaderMsg().getText();
				if(headerInfo2.contains(lastName)) {
					Reporter.log(lastName+" created successfully", true);
				}else {
					Reporter.log("Failed to create "+lastName, true);
				}
				
			}

}
