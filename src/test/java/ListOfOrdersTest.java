import io.restassured.response.Response;
import org.junit.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest extends EndPoints{

    @Test
    public void listOfOrders(){
        Response response =
                given()
                        .queryParam("limit","2").get(BASE_URI + ORDERS);
        response.then().statusCode(HTTP_OK).body("orders[0].id",equalTo(5)).body("orders.firstName",equalTo(List.of("Дарья", "Иван")));

    }


    @Test
    public void listOfOrdersWrongId(){

        Response response =
                given()
                        .queryParam("courierId","1").get(BASE_URI + ORDERS);
        response.then().statusCode(HTTP_NOT_FOUND).body("code", equalTo(404)).body("message", equalTo("Курьер с идентификатором 1 не найден"));

    }




}
