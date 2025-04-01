package Tests;

import Drivers.DriverManager;
import Pages.HomePage;
import Pages.ProductPage;
import Utils.JsonUtils;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import Listeners.TestNGListeners;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Random;

import static Drivers.DriverManager.getDriver;
import static Utils.PropertiesUtils.getPropertyValue;
import static Utils.TimestampUtils.getTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("Website")
@Listeners(TestNGListeners.class)
public class E2ETest {

    //Variables
    WebDriver driver;
    JsonUtils userData;
    JsonUtils productData;
    JsonUtils checkoutData;
    private final String username = "abdelrahman-" + getTimestamp();
    private final String cardNumber = new Faker().finance().creditCard();
    private final String expMonth = String.valueOf(new Random().nextInt(12) + 1);
    private final String expYear = String.valueOf(new Faker().number().numberBetween(2026, 2030));

    //Methods
    @BeforeClass
    public void setup() {
        productData = new JsonUtils("products-data");
        userData = new JsonUtils("auth-data");
        checkoutData = new JsonUtils("checkout-data");
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
    }

    @Test(description = "Valid User Registration")
    @Description("Verify that a user can successfully register with a valid username and password.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Register")
    public void createAccountTC() {
        new HomePage(driver).navigateToRegisterPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnSignupButton()
                .assertAlertMessage(userData.getJsonData("messages.successRegisterMessage"))
                .confirmAlert();
    }

    @Test(dependsOnMethods = "createAccountTC", description = "Valid User Login")
    @Description("Verify that a user can successfully log in with valid credentials.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Login")
    public void userLoginTC() {
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton();
        new HomePage(driver)
                .assertUserIsLoggedIn(username);
    }

    @Test(dependsOnMethods = "userLoginTC", description = "Add Product to Cart")
    @Description("Verify that user can add a product to cart.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Add to cart")
    public void addProductToCartTC() {
        new HomePage(driver)
                .viewProductPage(productData.getJsonData("products.product1.name"))
                .assertProductDetails(productData.getJsonData("products.product1.name"), productData.getJsonData("products.product1.price"))
                .addProductToCart()
                .assertAlertMessage(productData.getJsonData("messages.addingToCartMessage"))
                .confirmAlert();
    }

    @Test(dependsOnMethods = "addProductToCartTC", description = "Valid Order Checkout")
    @Description("Verify that user can checkout and purchase his order successfully.")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Checkout")
    public void checkoutOrderTC() {
        new ProductPage(driver)
                .navigateToCart()
                .assertProductDetails(productData.getJsonData("products.product1.name"), productData.getJsonData("products.product1.price"))
                .assertTheTotalPrice()
                .clickOnCheckoutButton()
                .enterName(checkoutData.getJsonData("billingInfo.name"))
                .enterCountry(checkoutData.getJsonData("billingInfo.country"))
                .enterCity(checkoutData.getJsonData("billingInfo.city"))
                .enterCardNumber(cardNumber)
                .enterExpiryMonth(expMonth)
                .enterExpiryYear(expYear)
                .clickOnPurchaseButton()
                .assertSuccessMessage(checkoutData.getJsonData("messages.successCheckoutMessage"))
                .assertSuccessfulPurchasing(checkoutData.getJsonData("billingInfo.name"), cardNumber)
                .clickOnOkButton();
    }

    @Test(dependsOnMethods = "checkoutOrderTC", description = "Valid User Logout")
    @Description("Verify that user can logout successfully.")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Logout")
    public void logoutTC() {
        new HomePage(driver)
                .clickOnLogoutLink()
                .assertUserIsLoggedOut();
    }

    @AfterClass
    public void quit() {
        DriverManager.quit();
    }
}
