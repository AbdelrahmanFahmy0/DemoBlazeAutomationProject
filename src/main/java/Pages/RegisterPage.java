package Pages;

import Utils.BrowserActions;
import Utils.ElementActions;
import Utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    //Variables
    private final WebDriver driver;

    //Locators
    private final By username = By.id("sign-username");
    private final By password = By.id("sign-password");
    private final By signUpButton = By.cssSelector("button[onclick=\"register()\"]");
    private final By cancelButton = By.xpath("//button[@onclick=\"register()\"]/parent::div/button[@data-dismiss=\"modal\"]");

    //Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Enter username: {0}")
    public RegisterPage enterUsername(String usernameText) {
        ElementActions.sendData(driver, username, usernameText);
        return this;
    }

    @Step("Enter password: {0}")
    public RegisterPage enterPassword(String passwordText) {
        ElementActions.sendData(driver, password, passwordText);
        return this;
    }

    @Step("Click on sign up button")
    public RegisterPage clickOnSignupButton() {
        ElementActions.clickElement(driver, signUpButton);
        return this;
    }

    @Step(("Click on cancel button"))
    public HomePage clickOnCancelButton() {
        ElementActions.clickElement(driver, cancelButton);
        return new HomePage(driver);
    }

    @Step("Confirm the alert")
    public HomePage confirmAlert() {
        BrowserActions.acceptAlert(driver);
        return new HomePage(driver);
    }

    //Validation
    @Step("Assert the text of alert")
    public RegisterPage assertAlertMessage(String message) {
        Validations.validateEquals(BrowserActions.getAlertText(driver)
                , message, "The alert message isn't as expected");
        return this;
    }
}
