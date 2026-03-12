package CartOperations;

public class EndPoints {

    public static final String BASE_URL = "https://www.shoppersstack.com/shopping";

    public static final String GET_CART = "/shoppers/{shopperId}/carts";

    public static final String ADD_CART = "/shoppers/{shopperId}/carts";

    public static final String UPDATE_CART = "/shoppers/{shopperId}/carts/{itemId}";

    public static final String DELETE_CART = "/shoppers/{shopperId}/carts/{productId}";
}