import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.courier.Courier;
import model.courier.CourierApi;
import org.apache.http.HttpStatus;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginCurierTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @Test
    @DisplayName("Проверить, что курьер может авторизоваться и получить id") // имя теста
    @Description("Проверить, что курьер может авторизоваться и получить id") // описание теста
    public void authorizationCourierTest() {
        CourierApi courierApi = new CourierApi();
        Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        courierApi.sendPostRequest(courier);
        Response response = courierApi.sendPostRequestLogin(new Courier(LOGIN,PASSWORD,null));
        response.then().statusCode(SC_OK);
        assertTrue(response.path("id") != null);

    }

    @ParameterizedTest
    @MethodSource("authorizationData")
    @DisplayName("Проверить, что для авторизации нужно передать все обязательные поля") // имя теста
    @Description("Проверить, что для авторизации нужно передать все обязательные поля") // описание теста
    public void authorizationCourierWithFieldsTest(String login, String password) {
        CourierApi courierApi = new CourierApi();
        Courier courier = new Courier(login, password, null);
        Response response = courierApi.sendPostRequestLogin(courier);
        response.then().statusCode(SC_BAD_REQUEST);
        assertEquals("Недостаточно данных для входа", response.path("message"));

    }

    @ParameterizedTest
    @MethodSource("authorizationDataError")
    @DisplayName("Проверить, что система вернёт ошибку, если неправильно указать логин или пароль") // имя теста
    @Description("Проверить, что система вернёт ошибку, если неправильно указать логин или пароль") // описание теста
    public void authorizationCourierErrorTest(String login, String password) {
        Courier courier = new Courier(login, password, null);
        CourierApi courierApi = new CourierApi();
        Response response = courierApi.sendPostRequestLogin(courier);
        response.then().statusCode(SC_NOT_FOUND);
        assertEquals("Учетная запись не найдена", response.path("message"));
    }


    private static Stream<Arguments> authorizationData() {
        return Stream.of(
                Arguments.of("", PASSWORD),
                Arguments.of(LOGIN, "")

        );
    }

    private static Stream<Arguments> authorizationDataError() {
        return Stream.of(
                Arguments.of(LOGIN, PASSWORD_2),
                Arguments.of(LOGIN_2, PASSWORD),
                Arguments.of(LOGIN_2, PASSWORD_2)

        );
    }

    @AfterEach
    public void tearDown() {
        CourierApi courierApi = new CourierApi();
        courierApi.deleteCourier(new Courier(LOGIN, PASSWORD, null));

    }


}