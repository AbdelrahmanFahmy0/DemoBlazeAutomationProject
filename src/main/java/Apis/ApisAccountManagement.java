package Apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ApisAccountManagement {

    //Variables
    // Base URL for the API that the service interacts with
    private final String BASE_URL = "https://api.demoblaze.com";

    //Constructor
    public ApisAccountManagement(){
        RestAssured.baseURI = BASE_URL;
    }

    //Services
    // Endpoint for creating a new account
    private final String createAccount_serviceName = "/signup";
    // Endpoint for logging to account
    private final String loginToAccount_serviceName= "/login";

    //Actions
    public Response createAccount(String username , String password){
        // Build and send the POST request with the username and password as JSON payload
        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{"
                        + "\"username\":\"" + username + "\","
                        + "\"password\":\"" + password + "\""
                        + "}")
                .when()
                .post(createAccount_serviceName);
        return response;
    }

    public Response login(String username , String password){
        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{"
                        + "\"username\":\"" + username + "\","
                        + "\"password\":\"" + password + "\""
                        + "}")
                .when()
                .post(loginToAccount_serviceName);
        return response;
    }
}
