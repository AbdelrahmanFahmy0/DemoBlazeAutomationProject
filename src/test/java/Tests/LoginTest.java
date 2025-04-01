package Tests;

import Apis.ApisAccountManagement;
import Drivers.DriverManager;
import Listeners.TestNGListeners;
import Pages.HomePage;
import Utils.JsonUtils;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static Drivers.DriverManager.getDriver;
import static Utils.PropertiesUtils.getPropertyValue;
import static Utils.TimestampUtils.getTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("Website")
@Feature("Login")
@Listeners(TestNGListeners.class)
public class LoginTest {

    //Variables
    WebDriver driver;
    JsonUtils userData;
    private final String username = "abdelrahman-" + getTimestamp();

    //Methods
    @BeforeClass
    public void beforeClass() {
        userData = new JsonUtils("auth-data");
        // Creating a new user account using Api
        new ApisAccountManagement().createAccount(username, userData.getJsonData("user.encodedPassword"));
    }

    @BeforeMethod
    public void setup() {
        // Initialize the WebDriver and navigate to the base URL.
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
    }

    @Test(priority = 1, description = "Valid User Login")
    @Description("Verify that a user can successfully log in with valid credentials.")
    @Severity(SeverityLevel.BLOCKER)
    public void validLoginTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton();
        new HomePage(driver)
                .assertUserIsLoggedIn(username);
    }

    @Test(priority = 2, description = "Login with Invalid Username")
    @Description("Verify that login fails when using an invalid username with a valid password.")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithInvalidUsernameTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(userData.getJsonData("inValidaData.username"))
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.nonExistedUserMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Login with Invalid Password")
    @Description("Verify that login fails when using an valid username with a invalid password.")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithInvalidPasswordTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("inValidaData.password"))
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.wrongPasswordMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Login with Invalid Data")
    @Description("Verify that login fails when using both an invalid username and an invalid password.")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidDataTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(userData.getJsonData("inValidaData.username"))
                .enterPassword(userData.getJsonData("inValidaData.password"))
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.nonExistedUserMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Login with Empty Username")
    @Description("Verify that login fails when the username field is left empty.")
    @Severity(SeverityLevel.MINOR)
    public void loginWithEmptyUsernameTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Login with Empty Password")
    @Description("Verify that login fails when the password field is left empty.")
    @Severity(SeverityLevel.MINOR)
    public void loginWithEmptyPasswordTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Login with Empty Data")
    @Description("Verify that login fails when both the username and password fields are left empty.")
    @Severity(SeverityLevel.MINOR)
    public void loginWithEmptyDataTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .clickOnLoginButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @AfterMethod
    public void quit() {
        DriverManager.quit();
    }
}
