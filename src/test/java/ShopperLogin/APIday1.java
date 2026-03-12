package ShopperLogin;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

import java.net.ResponseCache;
import java.util.HashMap;

public class APIday1 {
	
	String jwttoken;
	String shopperid;
	
	@Test(priority = 1, enabled = false)
	public void logintest() {
		RestAssured
		.given()
		.contentType("application/json")
		.relaxedHTTPSValidation()
		.body("{\r\n"
				+ "  \"email\": \"sabyashachi@gmail.com\",\r\n"
				+ "  \"password\": \"Sabya01#\",\r\n"
				+ "  \"role\": \"SHOPPER\"\r\n"
				+ "}")
		.when()
		.post("https://www.shoppersstack.com/shopping/users/login")
		
		.then()
		.statusCode(200)
		.log().all();
	}
	
	
	
	
	
	
	@Test(priority = 3)
	public void loginTest() {
		
		HashMap<String, String> hm = new HashMap<>();

        hm.put("email", "sabyashachi@gmail.com");
        hm.put("password", "Sabya01#");
        hm.put("role", "SHOPPER");

        Response res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body(hm)
        .when()
                .post("https://www.shoppersstack.com/shopping/users/login");
        shopperid=res.jsonPath().getString("data.userId");
        System.out.println("shopperid is nothing but userid:"+shopperid);
        jwttoken=res.jsonPath().getString("data.jwtToken");
        System.out.println("jwt token:"+jwttoken);
        res.then()
                .assertThat()
                .statusCode(200)
                .log().all();
	}
	
	
	@Test(priority=4, dependsOnMethods = "logintest", invocationCount = 4, enabled = true)
	public void fetchData() {
		given()
		.contentType("application/json")
		.auth().oauth2(jwttoken)
		.pathParam("shopperid", shopperid)
		.baseUri("https://www.shoppersstack.com/shopping")
		.relaxedHTTPSValidation()
		.when()
		.get("/shoppers/{shopperid}");
		
		
	}
}
