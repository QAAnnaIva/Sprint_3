import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.EndPoints.ORDERS;

public class ListOfOrdersTest  {
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void listOfOrders(){
        Response response =
                given()
                        .queryParam("limit","2").get(ORDERS);
        response.then().statusCode(HTTP_OK).body("orders[0].id",equalTo(5)).body("orders.firstName",equalTo(List.of("Дарья", "Иван")));

    }


    @Test
    public void listOfOrdersWrongId(){

        Response response =
                given()
                        .queryParam("courierId","1").get(ORDERS);
        response.then().statusCode(HTTP_NOT_FOUND).body("code", equalTo(404)).body("message", equalTo("Курьер с идентификатором 1 не найден"));

    }




}
