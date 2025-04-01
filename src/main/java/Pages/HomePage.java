package Pages;

import Utils.ElementActions;
import Utils.LogsUtil;
import Utils.Validations;
import Utils.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    //Variables
    private final WebDriver driver;

    //Locators
    private final By registerLink = By.id("signin2");
    private final By loginLink = By.id("login2");
    private final By logoutLink = By.id("logout2");
    private final By nameOfUser = By.id("nameofuser");

    //Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Navigate to registration page")
    public RegisterPage navigateToRegisterPage() {
        ElementActions.clickElement(driver, registerLink);
        LogsUtil.info("Navigation to the registration page");
        return new RegisterPage(driver);
    }

    @Step("Navigate to login page")
    public LoginPage navigateToLoginPage() {
        ElementActions.clickElement(driver, loginLink);
        LogsUtil.info("Navigation to the login page");
        return new LoginPage(driver);
    }

    @Step("Get the name of the user displayed on the homepage.")
    public String getNameOfUser(){
        return ElementActions.getText(driver, nameOfUser);
    }

    @Step("View the product page of: {0}")
    public ProductPage viewProductPage(String productName) {
        try {
            Waits.waitForAttribute(driver, nameOfUser, "style", "display: block;");
            By product = By.xpath("//h4[@class=\"card-title\"]//a[ text() = '" + productName + "']");
            ElementActions.clickElement(driver, product);
            LogsUtil.info("Viewing the page of product ", productName);
            return new ProductPage(driver);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            LogsUtil.error("The ", productName, " product isn't found");
            return null;
        }
    }

    @Step("Click on logout")
    public HomePage clickOnLogoutLink(){
        Waits.waitForAttribute(driver, logoutLink, "style", "display: block;");
        ElementActions.clickElement(driver,logoutLink);
        return this;
    }

    //Validation
    @Step("Assert the name of user is displayed at home page")
    public HomePage assertUserIsLoggedIn(String username) {
        Waits.waitForAttribute(driver, nameOfUser, "style", "display: block;");
        String nameOnHome = getNameOfUser();
        Validations.validateEquals(nameOnHome, "Welcome " + username, "The name isn't as expected");
        return this;
    }

    @Step("Assert the user has been logged out")
    public HomePage assertUserIsLoggedOut() {
        Waits.waitForAttribute(driver, loginLink, "style", "display: block;");
        Validations.validateTrue(ElementActions.findElement(driver,loginLink).isDisplayed(), "The user isn't logged out");
        Validations.validateFalse(ElementActions.findElement(driver,logoutLink).isDisplayed(), "The user isn't logged out");
        return this;
    }

}
