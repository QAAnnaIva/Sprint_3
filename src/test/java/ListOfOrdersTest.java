import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

    }


    @Test
    public void listOfOrders(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .when()
                        .get("/api/v1/orders?limit=2");
        response.then().assertThat().statusCode(200).body("orders[0].id",equalTo(5)).body("orders.firstName",equalTo(List.of("Дарья", "Иван")));

    }


    @Test
    public void listOfOrdersWrongId(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .when()
                        .get("api/v1/orders?courierId=1");
        response.then().assertThat().statusCode(404).body("code", equalTo(404)).body("message", equalTo("Курьер с идентификатором 1 не найден"));

    }




}
