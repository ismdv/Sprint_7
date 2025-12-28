import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.order.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListOrdersTests {


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @Test
    @DisplayName("Проверить, что в тело ответа возвращается список заказов.") // имя теста
    @Description("Проверить, что в тело ответа возвращается список заказов.") // описание теста
    public void returnBodyOfListOrders() {

        Orders orders = sendGetRequestOrder()
                .body()
                .as(Orders.class);

        assertTrue(orders.getOrders().length != 0);

    }


    @Step("Send POST request to /v1/orders?courierId=1")
    public Response sendGetRequestOrder() {
        return given()

                .header("Content-type", "application/json")
                .get("/api/v1/orders");
    }
}
