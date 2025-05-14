package objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
	WebDriver driver;
	
	public Login(WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "user_name")
	private WebElement usernameTF;
	
	@FindBy(name = "user_password")
	private WebElement passwordTf;
	
	@FindAll({@FindBy(id = "submitButton"), @FindBy(xpath = "//input[@value='Login' and @type='submit']")})
	private WebElement loginBtn;

	public WebElement getUsernameTF() {
		return usernameTF;
	}

	public WebElement getPasswordTf() {
		return passwordTf;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

	
	
}
