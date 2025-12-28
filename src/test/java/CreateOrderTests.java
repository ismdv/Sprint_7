import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.order.OrderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CreateOrderTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @ParameterizedTest
    @MethodSource("colorData")
    @DisplayName("Проверить, что можно указать один из цветов — BLACK или GREY") // имя теста
    @Description("Проверить, что можно указать один из цветов — BLACK или GREY") // описание теста
    public void createOrderWithOneColor(String clr, int code) {
        String[] color = {clr};
        //color.add("BLACK");
        OrderModel orderModel = new OrderModel(
                "Иван",
                "Иванов",
                "Мира, д.28",
                "5",
                "+7 495 555 44 22",
                5,
                "2025-12-12",
                "С наступающим новым годом!",
                color);

       sendPostRequestOrder(orderModel).then().statusCode(code);
    }
@Test
    @DisplayName("Проверить, что можно указать оба цвета") // имя теста
    @Description("Проверить, что можно указать оба цвета") // описание теста
    public void createOrderWithTwoColor() {
        String[] color = {"BLACK","GRAY"};
        //color.add("BLACK");
        OrderModel orderModel = new OrderModel(
                "Иван",
                "Иванов",
                "Мира, д.28",
                "5",
                "+7 495 555 44 22",
                5,
                "2025-12-12",
                "С наступающим новым годом!",
                color);

        sendPostRequestOrder(orderModel).then().statusCode(201);
    }
    @Test
    @DisplayName("Проверить, что можно совсем не указывать цвет") // имя теста
    @Description("Проверить, что можно совсем не указывать цвет") // описание теста
    public void createOrderWithoutColors() {
        String[] color = {};
        //color.add("BLACK");
        OrderModel orderModel = new OrderModel(
                "Иван",
                "Иванов",
                "Мира, д.28",
                "5",
                "+7 495 555 44 22",
                5,
                "2025-12-12",
                "С наступающим новым годом!",
                color);

        sendPostRequestOrder(orderModel).then().statusCode(201);
    }

    @Test
    @DisplayName("Проверить, что можно совсем не указывать цвет") // имя теста
    @Description("Проверить, что можно совсем не указывать цвет") // описание теста
    public void returnOrderTrack() {
        String[] color = {};
        //color.add("BLACK");
        OrderModel orderModel = new OrderModel(
                "Иван",
                "Иванов",
                "Мира, д.28",
                "5",
                "+7 495 555 44 22",
                5,
                "2025-12-12",
                "С наступающим новым годом!",
                color);

       int track = sendPostRequestOrder(orderModel).path("track");
    }

    private static Stream<Arguments> colorData() {
        return Stream.of(
                Arguments.of("BLACK", 201),
                Arguments.of("GRAY",  201)


        );
    }

    @Step("Send POST request to /api/v1/orders")
    public Response sendPostRequestOrder(OrderModel order) {
        return given()
                .contentType(JSON)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}

