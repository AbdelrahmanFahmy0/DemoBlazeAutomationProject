package Utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementActions {

    //Send data to element
    @Step("Sending data: {data} to element: {locator}")
    public static void sendData(WebDriver driver, By locator, String data){
        Waits.waitForElementVisible(driver,locator);
        Scrolling.scrollToElement(driver,locator);
        findElement(driver,locator).sendKeys(data);
        LogsUtil.info("Data entered: " , data , " in the field: ", locator.toString());
    }

    //Click on element
    @Step("Clicking on element: {locator}")
    public static void clickElement(WebDriver driver, By locator){
        Waits.waitForElementClickable(driver,locator);
        Scrolling.scrollToElement(driver,locator);
        findElement(driver,locator).click();
        LogsUtil.info("Clicked on the element: ", locator.toString());
    }

    //Get the text of element
    @Step("Getting the text from element: {locator}")
    public static String getText(WebDriver driver, By locator){
        Waits.waitForElementVisible(driver,locator);
        Scrolling.scrollToElement(driver,locator);
        LogsUtil.info("Getting text from the element: ", locator.toString() , " Text is ", findElement(driver,locator).getText());
        return findElement(driver,locator).getText();
    }

    //Finding element
    public static WebElement findElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }
}
