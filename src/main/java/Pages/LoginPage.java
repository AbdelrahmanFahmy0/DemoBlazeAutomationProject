package Pages;

import Utils.BrowserActions;
import Utils.ElementActions;
import Utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    //Variables
    private final WebDriver driver;

    //Locators
    private final By username = By.id("loginusername");
    private final By password = By.id("loginpassword");
    private final By loginButton = By.xpath("//button[@onclick=\"logIn()\"]");
    private final By cancelButton = By.xpath("//button[@onclick=\"logIn()\"]//parent::div/button[@data-dismiss=\"modal\"]");

    //Constructor
    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    //Actions
    @Step("Enter username: {0}")
    public LoginPage enterUsername(String usernameText) {
        ElementActions.sendData(driver, username, usernameText);
        return this;
    }

    @Step("Enter password: {0}")
    public LoginPage enterPassword(String passwordText) {
        ElementActions.sendData(driver, password, passwordText);
        return this;
    }

    @Step("Click on login button")
    public LoginPage clickOnLoginButton() {
        ElementActions.clickElement(driver, loginButton);
        return this;
    }

    @Step("Click on cancel button")
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
    public LoginPage assertAlertMessage(String message) {
        Validations.validateEquals(BrowserActions.getAlertText(driver)
                , message, "The alert message isn't as expected");
        return this;
    }
}
