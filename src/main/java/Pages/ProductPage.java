package Pages;

import Utils.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    //Variables
    private final WebDriver driver;

    //Locators
    private final By productName = By.xpath("//h2[@class=\"name\"]");
    private final By productPrice = By.xpath("//h3[@class='price-container']");
    private final By addToCartButton = By.xpath("//a[ text() = 'Add to cart']");
    private final By cartPageLink = By.id("cartur");

    //Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Get the name of product")
    private String getProductName() {
        return ElementActions.getText(driver, productName);
    }

    @Step("Get the price of product")
    private String getProductPrice() {
        return ElementActions.getText(driver, productPrice);
    }

    @Step("Add product to the cart")
    public ProductPage addProductToCart() {
        ElementActions.clickElement(driver, addToCartButton);
        LogsUtil.info("The ", productName.toString(), " product has been added to cart");
        return this;
    }

    @Step("Confirm the alert")
    public ProductPage confirmAlert() {
        BrowserActions.acceptAlert(driver);
        return this;
    }

    @Step("Navigate to the cart page")
    public CartPage navigateToCart() {
        ElementActions.clickElement(driver, cartPageLink);
        LogsUtil.info("Navigation to the cart page");
        return new CartPage(driver);
    }

    //Validations
    @Step("Assert the name and price of product")
    public ProductPage assertProductDetails(String expectedName, String expectedPrice) {
        String actualName = getProductName();
        String actualPrice = getProductPrice();
        String expPrice = "$" + expectedPrice + " *includes tax";
        SoftAssertion.getSoftAssert().assertEquals(actualName, expectedName, "The name of product isn't as expected");
        SoftAssertion.getSoftAssert().assertEquals(actualPrice, expPrice, "The price of product isn't as expected");
        return this;
    }

    @Step("Assert the text of alert")
    public ProductPage assertAlertMessage(String message) {
        Validations.validateEquals(BrowserActions.getAlertText(driver)
                , message, "The alert message isn't as expected");
        return this;
    }
}
