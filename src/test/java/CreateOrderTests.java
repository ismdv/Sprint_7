import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.order.OrderApi;
import model.order.OrderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateOrderTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @ParameterizedTest
    @MethodSource("colorData")
    @DisplayName("Проверить, что можно указать любую комбинацию цветов") // имя теста
    @Description("Проверить, что можно указать любую комбинацию цветов") // описание теста
    public void createOrderAnyoneColorTest(String color1, String color2) {
        String[] color = {color1, color2};
        OrderApi orderApi = new OrderApi();
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

        Response response = orderApi.sendPostRequestOrder(orderModel);
        response.then().statusCode(SC_CREATED);
        assertTrue(response.path("track") != null);
    }


    private static Stream<Arguments> colorData() {
        return Stream.of(
                Arguments.of("BLACK", null),
                Arguments.of(null, "GRAY"),
                Arguments.of(null, null),
                Arguments.of("BLACK", "GRAY")


        );
    }


}

