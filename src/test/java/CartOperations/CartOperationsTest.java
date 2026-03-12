package CartOperations;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import CartOperations.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import CartOperations.CartPojo;
import CartOperations.EndPoints;

public class CartOperationsTest extends BaseClass {

    String itemId;
    String productId;

    @Test(priority = 1)
    public void getCart() {

        Response res = given()
                .baseUri(EndPoints.BASE_URL)
                .auth().oauth2(jwtToken)
                .pathParam("shopperId", shopperId)
        .when()
                .get(EndPoints.GET_CART);

        res.then().statusCode(200).log().all();
    }

    @Test(priority = 2)
    public void addItemToCart() {

        CartPojo cart = new CartPojo(71,3);

        Response res = given()
                .baseUri(EndPoints.BASE_URL)
                .auth().oauth2(jwtToken)
                .pathParam("shopperId", shopperId)
                .contentType(ContentType.JSON)
                .body(cart)
        .when()
                .post(EndPoints.ADD_CART);

        itemId = res.jsonPath().getString("data.itemId");
        productId = res.jsonPath().getString("data.productId");

        res.then().statusCode(201).log().all();
    }

    @Test(priority = 3)
    public void updateCartItem() {

        CartPojo cart = new CartPojo(71,1);

        Response res = given()
                .baseUri(EndPoints.BASE_URL)
                .auth().oauth2(jwtToken)
                .pathParam("shopperId", shopperId)
                .pathParam("itemId", itemId)
                .contentType(ContentType.JSON)
                .body(cart)
        .when()
                .put(EndPoints.UPDATE_CART);

        res.then().statusCode(200).log().all();
    }

    @Test(priority = 4)
    public void deleteCartItem() {

        Response res = given()
                .baseUri(EndPoints.BASE_URL)
                .auth().oauth2(jwtToken)
                .pathParam("shopperId", shopperId)
                .pathParam("productId", productId)
        .when()
                .delete(EndPoints.DELETE_CART);

        res.then().statusCode(200).log().all();
        System.out.println("deleted");
        System.out.println("MyBranch");
        System.out.println("MyBranch is updated. need to merge touhsgchgaszdkhfcgzshkdcxgkhASFK master repo");
    }
    
}
