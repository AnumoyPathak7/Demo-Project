package ShopperWithPOJO;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ResponseCache;
import java.util.HashMap;
import java.util.List;

public class ShopperLogin extends BaseClass{

@Test
public void loginTest() {

PojoLogin pl = new PojoLogin("sabyashachi@gmail.com","Sabya01#","SHOPPER");

Response res = given()
        .relaxedHTTPSValidation()
        .contentType("application/json")
        .body(pl)
.when()
        .post("https://www.shoppersstack.com/shopping/users/login");

shopperid = res.jsonPath().getString("data.userId");
jwttoken = res.jsonPath().getString("data.jwtToken");

System.out.println("shopperid: "+shopperid);
System.out.println("jwt token: "+jwttoken);

res.then()
.statusCode(200)
.log().all();
}

@Test(dependsOnMethods="loginTest")
public void fetchData() {

Response res = given()
        .contentType("application/json")
        .auth().oauth2(jwttoken)
        .pathParam("shopperid", shopperid)
        .baseUri("https://www.shoppersstack.com/shopping")
        .relaxedHTTPSValidation()
.when()
        .get("/shoppers/{shopperid}");

res.then()
.statusCode(200)
.log().all();
}

@Test(dependsOnMethods="loginTest")
public void fetchProducts() throws IOException {

Response res = given()
        .contentType("application/json")
        .auth().oauth2(jwttoken)
        .baseUri("https://www.shoppersstack.com/shopping")
        .relaxedHTTPSValidation()
.when()
        .get("/products/alpha");

List<Integer> productId = res.jsonPath().getList("data.productId");

int pid = productId.get(1);

System.out.println("product id: "+pid);
FileWriter fw = new FileWriter("response.json");
fw.write(res.asPrettyString());
fw.close();

}
}