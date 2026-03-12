package CartOperations;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class testScript {

    String shopperid;
    String itemId;
    String jwttoken;
    String productId;

    @Test(priority = 1)
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

        shopperid = res.jsonPath().getString("data.userId");
        System.out.println("shopperid is nothing but userid: " + shopperid);

        jwttoken = res.jsonPath().getString("data.jwtToken");
        System.out.println("jwt token: " + jwttoken);

        res.then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }
    
    
     
    

    @Test(priority = 2)
    public void get() {

        Response res = given()
                .contentType("application/json")
                .auth().oauth2(jwttoken)
                .pathParam("shopperid", shopperid)
                .baseUri("https://www.shoppersstack.com/shopping")
                .relaxedHTTPSValidation()
        .when()
                .get("/shoppers/{shopperid}/carts");

        res.then()
                .statusCode(200)
                .log().all();
    }
    
    
    @Test( priority = 3)
    public void post() {
    	Response res = given()
                .contentType("application/json")
                .auth().oauth2(jwttoken)
                .pathParam("shopperid", shopperid)
                .baseUri("https://www.shoppersstack.com/shopping")
                .relaxedHTTPSValidation()
                .body("{\r\n"
                		+ "  \"productId\": 71,\r\n"
                		+ "  \"quantity\": 3\r\n"
                		+ "}")
        .when()
                .post("/shoppers/{shopperid}/carts");
    	itemId = res.jsonPath().getString("data.itemId");
    	productId = res.jsonPath().getString("data.productId");
        res.then()
                .statusCode(201)
                .log().all();
    }
    
    @Test(priority = 4)
    public void put() {
    	Response res = given()
                .contentType("application/json")
                .auth().oauth2(jwttoken)
                .pathParam("shopperid", shopperid)
                .pathParam("itemId",itemId)
                .baseUri("https://www.shoppersstack.com/shopping")
                .relaxedHTTPSValidation()
                .body("{\r\n"
                		+ "  \"productId\": 71,\r\n"
                		+ "  \"quantity\": 1\r\n"
                		+ "}")
        .when()
                .put("/shoppers/{shopperid}/carts/{itemId}");

        res.then()
                .statusCode(200)
                .log().all();
    	
    }
    @Test(priority = 5)
    public void delete() {
    	Response res = given()
                .contentType("application/json")
                .auth().oauth2(jwttoken)
                .pathParam("shopperid", shopperid)
                .pathParam("productId",productId)
                .baseUri("https://www.shoppersstack.com/shopping")
                .relaxedHTTPSValidation()
                
        .when()
                .delete("/shoppers/{shopperid}/carts/{productId}");

        res.then()
                .statusCode(200)
                .log().all();
    	
    }
}
    