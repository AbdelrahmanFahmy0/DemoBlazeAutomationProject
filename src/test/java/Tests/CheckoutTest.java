package Tests;

import Apis.ApisAccountManagement;
import Drivers.DriverManager;
import Pages.CartPage;
import Pages.HomePage;
import Utils.BrowserActions;
import Utils.CookieUtils;
import Utils.JsonUtils;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import Listeners.TestNGListeners;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Random;
import java.util.Set;

import static Drivers.DriverManager.getDriver;
import static Utils.PropertiesUtils.getPropertyValue;
import static Utils.TimestampUtils.getTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("Website")
@Feature("Checkout")
@Listeners(TestNGListeners.class)
public class CheckoutTest {

    //Variables
    WebDriver driver;
    JsonUtils userData;
    JsonUtils productData;
    JsonUtils checkoutData;
    private final String cardNumber = new Faker().finance().creditCard();
    private final String expMonth = String.valueOf(new Random().nextInt(12) + 1);
    private final String expYear = String.valueOf(new Faker().number().numberBetween(2026, 2030));
    private final String username = "abdelrahman-" + getTimestamp();
    private Set<Cookie> cookies;

    //Methods
    @BeforeClass
    public void beforeClass() {
        // Initialize test data from JSON files
        productData = new JsonUtils("products-data");
        userData = new JsonUtils("auth-data");
        checkoutData = new JsonUtils("checkout-data");
        // Creating a new user account using Api
        new ApisAccountManagement().createAccount(username, userData.getJsonData("user.encodedPassword"));
        // Set up the WebDriver and navigate to the base URL.
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
        // Retrieve cookies from the current browser session after login.
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton();
        cookies = CookieUtils.getCookies(driver);
        DriverManager.quit();
    }

    @BeforeMethod
    public void setup() {
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
        CookieUtils.addCookies(driver, cookies);
        BrowserActions.refreshPage(driver);
        new HomePage(driver)
                .viewProductPage(productData.getJsonData("products.product1.name"))
                .addProductToCart()
                .confirmAlert()
                .navigateToCart();
    }

    @Test(priority = 1, description = "Valid Order Checkout")
    @Description("Verify that user can checkout and purchase his order successfully.")
    @Severity(SeverityLevel.BLOCKER)
    public void validCheckoutTC() {
        new CartPage(driver)
                .clickOnCheckoutButton()
                .enterName(checkoutData.getJsonData("billingInfo.name"))
                .enterCountry(checkoutData.getJsonData("billingInfo.country"))
                .enterCity(checkoutData.getJsonData("billingInfo.city"))
                .enterCardNumber(cardNumber)
                .enterExpiryMonth(expMonth)
                .enterExpiryYear(expYear)
                .clickOnPurchaseButton()
                .assertSuccessMessage(checkoutData.getJsonData("messages.successCheckoutMessage"))
                .assertSuccessfulPurchasing(checkoutData.getJsonData("billingInfo.name"), cardNumber);
    }

    @Test(priority = 2, description = "Checkout without Providing Data")
    @Description("Verify that user can't checkout without entering billing information.")
    @Severity(SeverityLevel.CRITICAL)
    public void checkoutWithoutProvidingDataTC() {
        new CartPage(driver)
                .clickOnCheckoutButton()
                .clickOnPurchaseButton()
                .assertAlertMessage(checkoutData.getJsonData("messages.inCompleteInformationMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Checkout without Providing Name")
    @Description("Verify that user can't checkout without entering name.")
    @Severity(SeverityLevel.CRITICAL)
    public void checkoutWithoutProvidingNameTC() {
        new CartPage(driver)
                .clickOnCheckoutButton()
                .enterCountry(checkoutData.getJsonData("billingInfo.country"))
                .enterCity(checkoutData.getJsonData("billingInfo.city"))
                .enterCardNumber(cardNumber)
                .enterExpiryMonth(expMonth)
                .enterExpiryYear(expYear)
                .clickOnPurchaseButton()
                .assertAlertMessage(checkoutData.getJsonData("messages.inCompleteInformationMessage"))
                .confirmAlert();
    }

    @Test(priority = 2, description = "Checkout without Providing Card Number")
    @Description("Verify that user can't checkout without entering card number.")
    @Severity(SeverityLevel.CRITICAL)
    public void checkoutWithoutProvidingCardNumberTC() {
        new CartPage(driver)
                .clickOnCheckoutButton()
                .enterName(checkoutData.getJsonData("billingInfo.name"))
                .enterCountry(checkoutData.getJsonData("billingInfo.country"))
                .enterCity(checkoutData.getJsonData("billingInfo.city"))
                .enterExpiryMonth(expMonth)
                .enterExpiryYear(expYear)
                .clickOnPurchaseButton()
                .assertAlertMessage(checkoutData.getJsonData("messages.inCompleteInformationMessage"))
                .confirmAlert();
    }

    @AfterMethod
    public void quit() {
        DriverManager.quit();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }

}
