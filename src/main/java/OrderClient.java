import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String COURIER_PATH = "/orders/";

    @Step
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(COURIER_PATH)
                .then();
    }

    @Step
    public void cancel(String trackId) {
        given()
                .spec(getBaseSpec())
                .body(trackId)
                .when()
                .put(COURIER_PATH + "cancel");
    }

}
