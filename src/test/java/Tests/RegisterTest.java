package Tests;

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
@Feature("Register")
@Listeners(TestNGListeners.class)
public class RegisterTest {

    //Variables
    WebDriver driver;
    JsonUtils userData;
    private final String username = "abdelrahman-" + getTimestamp();

    //Methods
    @BeforeClass
    public void beforeClass() {
        userData = new JsonUtils("auth-data");
    }

    @BeforeMethod
    public void setup() {
        // Initialize the WebDriver and navigate to the base URL.
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
    }

    @Test(priority = 1, description = "Valid User Registration")
    @Description("Verify that a user can successfully register with a valid username and password.")
    @Severity(SeverityLevel.BLOCKER)
    public void validRegistrationTC() {
        new HomePage(driver).navigateToRegisterPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.successRegisterMessage"))
                .confirmAlert();
    }

    @Test(dependsOnMethods = "validRegistrationTC", priority = 1, description = "Register with Existing Username")
    @Description("Verify that the system prevents registration using an already existing username.")
    @Severity(SeverityLevel.CRITICAL)
    public void registerWithExistingUsernameTC() {
        new HomePage(driver).navigateToRegisterPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.existedUsernameMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Register with Empty Username")
    @Description("Verify that registration fails when the username field is left empty.")
    @Severity(SeverityLevel.MINOR)
    public void registerWithEmptyUsernameTC() {
        new HomePage(driver).navigateToRegisterPage()
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Register with Empty Password")
    @Description("Verify that registration fails when the password field is left empty.")
    @Severity(SeverityLevel.MINOR)
    public void registerWithEmptyPasswordTC() {
        new HomePage(driver).navigateToRegisterPage()
                .enterUsername(username)
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Register with Empty Fields")
    @Description("Verify that registration fails when both the username and password fields are left empty.")
    @Severity(SeverityLevel.MINOR)
    public void registerWithEmptyFieldsTC() {
        new HomePage(driver).navigateToRegisterPage()
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.emptyFieldMessage"))
                .confirmAlert();
    }

    @AfterMethod
    public void quit() {
        DriverManager.quit();
    }

}
