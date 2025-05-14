package vtigerUsingTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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
import objectRepository.HomePage;
import objectRepository.LoginPage;
import objectRepository.NewOrgPage;
import objectRepository.OrgInfoPage;
import objectRepository.OrganizationPage;
@Listeners(listenerUtility.ListenerImplementationClass.class)
public class CreateOrgWithIndustryAndTypeUsingTestNGTest extends BaseClass{
     @Test(groups = {"Smoke Test","Regresstion Test"})
	public void createOrgWithIndustryAndTypeTest() throws EncryptedDocumentException, IOException {
		
		ExcelFileUtility eutil= new ExcelFileUtility();
		JavaUtility jutil= new JavaUtility();
		WebDriverUtility wutil= new WebDriverUtility();
		
				//Generate Random Number
				int randomNum = jutil.getRandomNumber();
				
				//Read the Test script data from excel file
				String orgName = eutil.readDataFromExcelFile("Org", 1, 2)+randomNum;
				String industry= eutil.readDataFromExcelFile("Org", 1, 5);
				String type= eutil.readDataFromExcelFile("Org", 1, 6);
				
				
				wutil.waitForPageToLoad(driver);
				
				//Navigate to organization module
				HomePage hp= new HomePage(driver);
				hp.getOrglink().click();
								
				//Click on "Create Org" lookup image
				OrganizationPage op= new OrganizationPage(driver);
				op.getNewOrg().click();
				
				//Create org with mandatory details
				NewOrgPage nop= new NewOrgPage(driver);
				nop.createOrg(orgName, industry, type);
				
				
				//Verification
				OrgInfoPage oip= new OrgInfoPage(driver);
				String headerInfo =oip.getHeaderText().getText();
				if(headerInfo.contains(orgName)) {
					Reporter.log(orgName+" created successfully", true);
				}else {
					Reporter.log("Failed to create "+orgName, true);
				}
				
				//Verifying Industry and Type
				String actIndustry = oip.getIndustryHeader().getText();
				if(actIndustry.equals(industry)) {
					Reporter.log(industry+" info is verified successfully", true);
				}else {
					Reporter.log(industry+" info failed to verify", true);
				}
				String actType = oip.getTypeHeader().getText();
				if(actType.equals(type)) {
					Reporter.log(type+" info is verified successfully", true);
				}else {
					Reporter.log(type+" info failed to verify", true);
				}
				
	}

}
