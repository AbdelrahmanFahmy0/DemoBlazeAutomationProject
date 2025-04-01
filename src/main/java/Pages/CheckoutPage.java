package Pages;

import Utils.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    //Variables
    private final WebDriver driver;

    //Locators
    private final By name = By.id("name");
    private final By country = By.id("country");
    private final By city = By.id("city");
    private final By cardNumber = By.id("card");
    private final By expiryMonth = By.id("month");
    private final By expiryYear = By.id("year");
    private final By purchaseButton = By.xpath("//button[@onclick='purchaseOrder()']");
    private final By message = By.xpath("//div[contains(@class,'sweet-alert')]/h2");
    private final By fullUserInfo = By.xpath("//div[contains(@class,'sweet-alert')]/p");
    private final By okButton = By.xpath("//div[contains(@class,'sweet-alert')]//button[@tabindex='1']");

    //Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Enter name: {0}")
    public CheckoutPage enterName(String nameText) {
        ElementActions.sendData(driver, name, nameText);
        return this;
    }

    @Step("Enter country: {0}")
    public CheckoutPage enterCountry(String countryText) {
        ElementActions.sendData(driver, country, countryText);
        return this;
    }

    @Step("Enter city: {0}")
    public CheckoutPage enterCity(String cityText) {
        ElementActions.sendData(driver, city, cityText);
        return this;
    }

    @Step("Enter card number: {0}")
    public CheckoutPage enterCardNumber(String numberText) {
        ElementActions.sendData(driver, cardNumber, numberText);
        return this;
    }

    @Step("Enter expiry month: {0}")
    public CheckoutPage enterExpiryMonth(String month) {
        ElementActions.sendData(driver, expiryMonth, month);
        return this;
    }

    @Step("Enter expiry year: {0}")
    public CheckoutPage enterExpiryYear(String year) {
        ElementActions.sendData(driver, expiryYear, year);
        return this;
    }

    @Step("Click on purchase button")
    public CheckoutPage clickOnPurchaseButton() {
        ElementActions.clickElement(driver, purchaseButton);
        return this;
    }

    @Step("Get the displayed message")
    private String getMessage() {
        return ElementActions.getText(driver, message);
    }

    @Step("Get the name of user")
    private String getUsername() {
        String fullText = ElementActions.getText(driver, fullUserInfo);
        String nameOfUser = fullText.split("Name:")[1].split("\n")[0].trim();
        LogsUtil.info("The name on success message is: ", nameOfUser);
        return nameOfUser;
    }

    @Step("Get the card number of user")
    private String getCardNumberOfUser() {
        String fullText = ElementActions.getText(driver, fullUserInfo);
        String cardNumberOfUser = fullText.split("Card Number:")[1].split("\n")[0].trim();
        LogsUtil.info("The card number on success message is: ", cardNumberOfUser);
        return cardNumberOfUser;
    }

    @Step("Get the total amount of order")
    private String getTotalAmountOfOrder() {
        String fullText = ElementActions.getText(driver, fullUserInfo);
        String totalAmountOfOrder = fullText.split("Amount:")[1].split("\n")[0].trim().split(" ")[0];
        LogsUtil.info("The total amount on success message is: ", totalAmountOfOrder);
        return totalAmountOfOrder;
    }

    @Step("Confirm the alert")
    public CheckoutPage confirmAlert() {
        BrowserActions.acceptAlert(driver);
        return this;
    }

    @Step("Click on ok button")
    public HomePage clickOnOkButton() {
        ElementActions.clickElement(driver, okButton);
        BrowserActions.refreshPage(driver);
        return new HomePage(driver);
    }

    //Validations
    @Step("Assert the displayed message")
    public CheckoutPage assertSuccessMessage(String message) {
        SoftAssertion.getSoftAssert().assertEquals(getMessage(), message);
        return this;
    }

    @Step("Assert the displayed data of user")
    public CheckoutPage assertSuccessfulPurchasing(String name, String cardNumber) {
        SoftAssertion.getSoftAssert().assertEquals(getUsername(), name);
        SoftAssertion.getSoftAssert().assertEquals(getCardNumberOfUser(), cardNumber);
        return this;
    }

    @Step("Assert the text of alert")
    public CheckoutPage assertAlertMessage(String message) {
        Validations.validateEquals(BrowserActions.getAlertText(driver)
                , message, "The alert message isn't as expected");
        return this;
    }

}
