import io.qameta.allure.Description;
import io.restassured.RestAssured;
import model.order.OrderApi;
import model.order.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListOrdersTests {


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @Test
    @DisplayName("Проверить, что в тело ответа возвращается список заказов.") // имя теста
    @Description("Проверить, что в тело ответа возвращается список заказов.") // описание теста
    public void returnBodyOfListOrdersTest() {
        OrderApi orderApi = new OrderApi();
        Orders orders = orderApi.sendGetRequestOrder()
                .body()
                .as(Orders.class);

        assertTrue(orders.getOrders().length != 0);

    }


}
