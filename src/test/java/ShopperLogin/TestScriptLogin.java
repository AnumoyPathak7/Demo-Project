package ShopperLogin;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TestScriptLogin extends BaseClass{
	
	@Test
	public void fetchProducts() {
		Response res=given()
		.contentType("application/json")
		.auth().oauth2(jwttoken)
		.baseUri("https://www.shoppersstack.com/shopping")
		.relaxedHTTPSValidation()
		.when()
		.get("/products/alpha");
		
		List<Integer> productId=res.jsonPath().getList("data.productId");
		int pid=productId.get(1);
		System.out.println("product id:"+pid);
		
		
	}
}
