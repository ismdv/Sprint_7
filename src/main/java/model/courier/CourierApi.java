package model.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;

public class CourierApi {

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
    public Response sendPostRequestId(Courier courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Send DELET request to /api/v1/courier/:id")
    public Response sendDeleteRequestId(String id) {
        return given()
                .when()
                .delete("api/v1/courier/" + id);
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendPostRequestLogin(Courier courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Delete courier")
    public void deleteCourier(Courier courier) {
        Response response = sendPostRequestId(courier);
        if (response.statusCode() != SC_NOT_FOUND) {
            sendDeleteRequestId(response.path("id").toString());
        }


    }

}





