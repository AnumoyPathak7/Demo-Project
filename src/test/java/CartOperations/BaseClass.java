package CartOperations;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;
import CartOperations.LoginPojo;

public class BaseClass {

    public static String shopperId;
    public static String jwtToken;

    @BeforeClass
    public void login() {

        LoginPojo loginBody = new LoginPojo(
                "sabyashachi@gmail.com",
                "Sabya01#",
                "SHOPPER");

        Response res = given()
                .baseUri("https://www.shoppersstack.com/shopping")
                .contentType("application/json")
                .body(loginBody)
        .when()
                .post("/users/login");

        shopperId = res.jsonPath().getString("data.userId");
        jwtToken = res.jsonPath().getString("data.jwtToken");

        System.out.println("ShopperID : " + shopperId);
        System.out.println("Token : " + jwtToken);
    }
}