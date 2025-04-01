package Tests;

import Apis.ApisAccountManagement;
import Drivers.DriverManager;
import Listeners.TestNGListeners;
import Pages.HomePage;
import Pages.ProductPage;
import Utils.JsonUtils;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static Drivers.DriverManager.getDriver;
import static Utils.PropertiesUtils.getPropertyValue;
import static Utils.TimestampUtils.getTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("Website")
@Feature("Adding Product to Cart")
@Listeners(TestNGListeners.class)
public class CartTest {

    //Variables
    WebDriver driver;
    JsonUtils userData;
    JsonUtils productData;
    private final String username = "abdelrahman-" + getTimestamp();

    //Methods
    @BeforeClass
    public void setup() {
        productData = new JsonUtils("products-data");
        userData = new JsonUtils("auth-data");
        // Creating a new user account using Api
        new ApisAccountManagement().createAccount(username, userData.getJsonData("user.encodedPassword"));
        // Initialize the WebDriver and navigate to the base URL.
        DriverManager.setupDriver();
        driver = getDriver();
        driver.get(getPropertyValue("BASE_URL"));
        // Perform login using valid credentials
        new HomePage(driver)
                .navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(userData.getJsonData("user.password"))
                .clickOnLoginButton();
    }

    @Test(description = "Check the Details of Product")
    @Description("Verify that the details of product are correct")
    @Severity(SeverityLevel.CRITICAL)
    public void checkProductDetailsTC() {
        new HomePage(driver)
                .viewProductPage(productData.getJsonData("products.product1.name"))
                .assertProductDetails(productData.getJsonData("products.product1.name"), productData.getJsonData("products.product1.price"));
    }

    @Test(dependsOnMethods = "checkProductDetailsTC", description = "Add Product to Cart")
    @Description("Verify that user can add a product to cart.")
    @Severity(SeverityLevel.BLOCKER)
    public void addProductToCartTC() {
        new ProductPage(driver)
                .addProductToCart()
                .assertAlertMessage(productData.getJsonData("messages.addingToCartMessage"))
                .confirmAlert();
    }

    @Test(dependsOnMethods = "addProductToCartTC", description = "Check the Details of Added Product In Cart")
    @Description("Verify that the product is added to the cart and the cart updates successfully.")
    @Severity(SeverityLevel.CRITICAL)
    public void checkAddedProductInCartTC() {
        new ProductPage(driver)
                .navigateToCart()
                .assertProductDetails(productData.getJsonData("products.product1.name"), productData.getJsonData("products.product1.price"))
                .assertTheTotalPrice();
    }

    @AfterClass
    public void quit() {
        DriverManager.quit();
    }
}
