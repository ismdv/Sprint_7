import io.qameta.allure.Description;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import model.courier.Courier;
import model.courier.CourierApi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.apache.http.HttpStatus.*;

import static model.courier.CourierData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCourierTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";


    }

    @Test
    @DisplayName("Проверка возможности создания курьера") // имя теста
    @Description("Проверить ручку создания курьера  /api/v1/courier") // описание теста
    public void createCourierTest() {
        CourierApi courierApi = new CourierApi();
        Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        Response response = courierApi.sendPostRequest(courier);
        assertEquals(SC_CREATED, response.statusCode());
        assertEquals(true, response.path("ok"));
    }

    @Test
    @DisplayName("Проверкак возможности создания двух одинаковых курьеров") // имя теста
    @Description("Проверить возможность создания двух одинаковых курьеров /api/v1/courier") // описание теста
    public void createSameCourierTest() {
        CourierApi courierApi = new CourierApi();
        Courier courier1 = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        Courier courier2 = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        Response response1 = courierApi.sendPostRequest(courier1);
        Response response2 = courierApi.sendPostRequest(courier2);
        assertEquals(SC_CREATED, response1.statusCode());
        assertEquals(true, response1.path("ok"));
        assertEquals(SC_CONFLICT, response2.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", response2.path("message"));
    }


    @ParameterizedTest
    @MethodSource("courierData")
    @DisplayName("Проверить, чтобы создать курьера, нужно передать в ручку все обязательные поля;") // имя теста
    @Description("Проверить, чтобы создать курьера, нужно передать в ручку все обязательные поля;") // описание теста
    public void createCourierSendAllFieldTest(String login, String password, String firstName) {
        CourierApi courierApi = new CourierApi();
        Response response = courierApi.sendPostRequest(new Courier(login, password, firstName));
        response.then().statusCode(SC_BAD_REQUEST);
        assertEquals("Недостаточно данных для создания учетной записи", response.path("message"));
    }


    @AfterEach
    public void tearDown() {
        CourierApi courierApi = new CourierApi();
        courierApi.deleteCourier(new Courier(LOGIN, PASSWORD, null));

    }


    private static Stream<Arguments> courierData() {
        return Stream.of(
                Arguments.of(null, PASSWORD, null),
                Arguments.of(LOGIN, null, null),
                Arguments.of(null, null, FIRST_NAME)


        );
    }
}



