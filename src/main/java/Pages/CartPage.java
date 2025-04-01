package Pages;

import Utils.ElementActions;
import Utils.LogsUtil;
import Utils.SoftAssertion;
import Utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {

    //Variables
    private final WebDriver driver;
    private float totalPriceOfProducts = 0;

    //Locators
    private final By productName = By.xpath("//td[2]");
    private final By productPrice = By.xpath("//td[3]");
    private final By totalPrice = By.id("totalp");
    private final By pricesOfProducts = By.xpath("//tr[@class=\"success\"]//td[3]");
    private final By checkoutButton = By.xpath("//button[@data-target='#orderModal']");

    //Constructor
    public CartPage(WebDriver driver) {
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

    @Step("Get the total price of products")
    private String getTotalProductsPrice() {
        return ElementActions.getText(driver, totalPrice);
    }

    private String calculateTotalPriceOfProducts() {
        try {
            List<WebElement> elements = driver.findElements(pricesOfProducts);
            for (int i = 1; i <= elements.size(); i++) {
                By prices = By.xpath("(//tr[@class=\"success\"]//td[3])[" + i + "]");
                String price = ElementActions.getText(driver, prices);
                totalPriceOfProducts = totalPriceOfProducts + Float.parseFloat(price);
            }
            LogsUtil.info("Total price " + totalPriceOfProducts);
            return String.valueOf((int) totalPriceOfProducts);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            return "0";
        }
    }

    @Step("Click on checkout button")
    public CheckoutPage clickOnCheckoutButton() {
        ElementActions.clickElement(driver, checkoutButton);
        return new CheckoutPage(driver);
    }

    //Validations
    @Step("Assert the name and price of product inside the cart")
    public CartPage assertProductDetails(String expectedName, String expectedPrice) {
        String actualName = getProductName();
        String actualPrice = getProductPrice();
        SoftAssertion.getSoftAssert().assertEquals(actualName, expectedName, "The name of product isn't as expected");
        SoftAssertion.getSoftAssert().assertEquals(actualPrice, expectedPrice, "The price of product isn't as expected");
        return this;
    }

    @Step("Assert the total price of products inside the cart")
    public CartPage assertTheTotalPrice() {
        String actualTotalPrice = getTotalProductsPrice();
        String expectedTotalPrice = calculateTotalPriceOfProducts();
        Validations.validateEquals(actualTotalPrice, expectedTotalPrice, "The total price of products isn't as expected");
        return this;
    }
}
