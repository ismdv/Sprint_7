import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.courier.Courier;
import model.courier.CourierCreds;
import model.courier.LoginWithoutLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static model.courier.CourierData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginCurierTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        Courier courier = new Courier(NAME, PASSWORD, FIRST_NAME);
        sendPostRequest(courier);
    }

    @Test
    @DisplayName("Проверить, что курьер может авторизоваться") // имя теста
    @Description("Проверить, что курьер может авторизоваться") // описание теста
    public void authorizationCourier() {
        CourierCreds courierCreds = new CourierCreds(NAME, PASSWORD);
        sendPostRequestLogin(courierCreds).then().statusCode(200);

    }

    @ParameterizedTest
    @MethodSource("authorizationData")
    @DisplayName("Проверить, что для авторизации нужно передать все обязательные поля") // имя теста
    @Description("Проверить, что для авторизации нужно передать все обязательные поля") // описание теста
    public void authorizationCourierWithField(String login, String password, int code) {
        CourierCreds courierCreds = new CourierCreds(login, password);
        sendPostRequestLogin(courierCreds).then().statusCode(code);

    }

    @ParameterizedTest
    @MethodSource("authorizationDataError")
    @DisplayName("Проверить, что система вернёт ошибку, если неправильно указать логин или пароль") // имя теста
    @Description("Проверить, что система вернёт ошибку, если неправильно указать логин или пароль") // описание теста
    public void authorizationCourierError(String login, String password, int code) {
        CourierCreds courierCreds = new CourierCreds(login, password);
        sendPostRequestLogin(courierCreds).then().statusCode(code);
    }

    @Test
    @DisplayName("Проверить, что если какого-то поля нет, запрос возвращает ошибку") // имя теста
    @Description("Проверить, что если какого-то поля нет, запрос возвращает ошибку") // описание теста
    public void authorizationCourierErrorWithoutFiealds() {
        LoginWithoutLogin login = new LoginWithoutLogin(PASSWORD);
        assertEquals(400, sendPostRequestLogin(login).statusCode());
    }

    @Test
    @DisplayName("Проверить, что если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    // имя теста
    @Description("Проверить, что если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    // описание теста
    public void authorizationCourierNotExisting() {
        CourierCreds courierCreds = new CourierCreds(NAME_2, PASSWORD_2);
        sendPostRequestLogin(courierCreds).then().statusCode(404);
    }

    @Test
    @DisplayName("Проверить, что успешный запрос возвращает id") // имя теста
    @Description("Проверить, что успешный запрос возвращает id") // описание теста
    public void authorizationCourierReturnId() {
        CourierCreds courierCreds = new CourierCreds(NAME, PASSWORD);
        int id = sendPostRequestLogin(courierCreds).path("id");
    }


    private static Stream<Arguments> authorizationData() {
        return Stream.of(
                Arguments.of("", PASSWORD, 400),
                Arguments.of(NAME, "", 400),
                Arguments.of(NAME, PASSWORD, 200)

        );
    }

    private static Stream<Arguments> authorizationDataError() {
        return Stream.of(
                Arguments.of(NAME, PASSWORD, 200),
                Arguments.of(NAME, PASSWORD_2, 404),
                Arguments.of(NAME_2, PASSWORD, 404)

        );
    }
/*@AfterEach
public void  tearDown(){
        deleteCourier(NAME,PASSWORD);
}*/

    @Step("Send POST request to /api/v1/courier")
    public Response sendPostRequest(Courier courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendPostRequestLogin(CourierCreds courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier/login");
    }


    @Step("Send POST request to /api/v1/courier/login without login")
    public Response sendPostRequestLogin(LoginWithoutLogin login) {
        return given()
                .contentType(JSON)
                .and()
                .body(login)
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public String sendPostRequestId(CourierCreds courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier/login")
                .path("id");
    }

    @Step("Send DELET request to /api/v1/courier/:id")
    public Response sendDeleteRequestId(String id) {
        return given()
                .when()
                .delete("api/v1/courier/" + id);
    }

    @Step("Удаление курьера")
    public void deleteCourier(String name, String password) {
        String id = sendPostRequestId(new CourierCreds(name, password));
        if (id != null) {
            sendDeleteRequestId(id);
        }
    }
}
