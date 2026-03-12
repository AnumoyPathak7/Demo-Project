package ShopperLogin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;

public class BaseClass {
	public String jwttoken;
	public String shopperid;
	
	@BeforeClass()
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

}
