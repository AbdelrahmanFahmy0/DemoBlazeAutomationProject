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
@Feature("Logout")
@Listeners(TestNGListeners.class)
public class LogoutTest {

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
        // Perform login using test data credentials.
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton();
    }

    @Test(description = "Valid User Logout")
    @Description("Verify that user can logout successfully.")
    @Severity(SeverityLevel.BLOCKER)
    public void checkUserLogoutTC() {
        new HomePage(driver)
                .clickOnLogoutLink()
                .assertUserIsLoggedOut();
    }

    @AfterMethod
    public void quit() {
        DriverManager.quit();
    }
}
