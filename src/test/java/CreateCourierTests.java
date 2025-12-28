import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.courier.Courier;
import model.courier.CourierCreds;
import model.courier.CourierWithoutOneField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static model.courier.CourierData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCourierTests {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @Test
    @DisplayName("Проверить ручку создания курьера") // имя теста
    @Description("Проверить ручку создания курьера") // описание теста
    public void createCourier() {
        Courier courier = new Courier(NAME, PASSWORD, FIRST_NAME);
        assertEquals(201, sendPostRequest(courier).statusCode());
        deleteCourier(NAME, PASSWORD);
    }

    @Test
    @DisplayName("Проверить возможность создания двух одинаковых курьеров") // имя теста
    @Description("Проверить возможность создания двух одинаковых курьеров") // описание теста
    public void createSameCourier() {

        Courier courier1 = new Courier(NAME, PASSWORD, FIRST_NAME);
        Courier courier2 = new Courier(NAME, PASSWORD, FIRST_NAME);
        assertEquals(201, sendPostRequest(courier1).statusCode());
        assertEquals(409, sendPostRequest(courier2).statusCode());
        deleteCourier(NAME, PASSWORD);
    }


    @Test
    @DisplayName("Проверить, чтобы создать курьера, нужно передать в ручку все обязательные поля;") // имя теста
    @Description("Проверить, чтобы создать курьера, нужно передать в ручку все обязательные поля;") // описание теста
    public void createCourierSendAllField() {


        Courier courier = new Courier("", "", FIRST_NAME);
        sendPostRequest(courier).statusCode();

    }

    @Test
    @DisplayName("Проверка правильности кода ответа") // имя теста
    @Description("Проверка правильности кода ответа") // описание теста
    public void assertCodeResponse() {

        Courier courier = new Courier(NAME, PASSWORD, FIRST_NAME);
        sendPostRequest(courier).then().statusCode(201);
        deleteCourier(NAME, PASSWORD);


    }

    @Test
    @DisplayName("Проверка успешного ответа ok:true") // имя теста
    @Description("Проверка успешного ответа ok:true") // описание теста
    public void answerOkTrue() {

        Courier courier = new Courier(NAME, PASSWORD, FIRST_NAME);
        assertEquals(true, sendPostRequest(courier).path("ok"));
        deleteCourier(NAME, PASSWORD);

    }

    @Test
    @DisplayName("Проверка: если одного из полей нет, запрос возвращает ошибку") // имя теста
    @Description("Проверка: если одного из полей нет, запрос возвращает ошибку") // описание теста
    public void createCourierSendWithoutOneField() {
        CourierWithoutOneField courier = new CourierWithoutOneField(PASSWORD, FIRST_NAME);
        sendPostRequest(courier).then().statusCode(400);
    }


    @Test
    @DisplayName("Проверка: если создать пользователя с логином, который уже есть, возвращается ошибка.") // имя теста
    @Description("Проверка: если создать пользователя с логином, который уже есть, возвращается ошибка.")
    // описание теста
    public void createCourierWithSomeLogin() {


        Courier courier1 = new Courier(NAME, PASSWORD, FIRST_NAME);
        Courier courier2 = new Courier(NAME, PASSWORD_2, FIRST_NAME_2);
        sendPostRequest(courier1).then().statusCode(201);
        sendPostRequest(courier2).then().statusCode(409);
        deleteCourier(NAME, PASSWORD);
    }


    @Step("Send POST request to /api/v1/courier")
    public Response sendPostRequest(Courier courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier without one field")
    public Response sendPostRequest(CourierWithoutOneField courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public String sendPostRequestId(CourierCreds courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier/login")
                .path("id")
                .toString();
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
