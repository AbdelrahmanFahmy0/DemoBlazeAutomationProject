package Utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class CookieUtils {

    //Retrieving all cookies from the WebDriver instance.
    public static Set<Cookie> getCookies(WebDriver driver) {
        // Wait for the "tokenp_" cookie to be available before retrieving cookies.
        Waits.waitForCookie(driver, "tokenp_");
        return driver.manage().getCookies();
    }

    //Adding cookies to the specified WebDriver instance.
    public static void addCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }

//    public static Set<Cookie> extractCookies(Response response) {
//        Set<Cookie> seleniumCookies = new HashSet<>();
//        //Extracting the token from response
//        String responseBody = response.getBody().asString();
//        String[] parts = responseBody.split(": ");
//        String token = parts[1].replace("\"", "");
//        //Create a Selenium Cookie with the extracted token
//        Cookie tokenCookie = new Cookie.Builder("tokenp_", token)
//                .domain("www.demoblaze.com")
//                .path("/")
//                .sameSite("Lax")
//                .build();
//        //Add the cookie to a Set
//        seleniumCookies.add(tokenCookie);
//        System.out.println("seleniumCookies is " + seleniumCookies);
//        return seleniumCookies;
//    }
}
