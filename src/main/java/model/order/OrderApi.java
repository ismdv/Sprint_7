package model.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderApi {

    @Step("Send POST request to /api/v1/orders")
    public Response sendPostRequestOrder(OrderModel order) {
        return given()
                .contentType(JSON)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }


    @Step("Send GET request to /v1/orders?courierId=1")
    public Response sendGetRequestOrder() {
        return given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders");
    }
}
