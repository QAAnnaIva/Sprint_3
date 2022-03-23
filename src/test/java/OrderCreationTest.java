import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.Track;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.EndPoints.ORDERS;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Number rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public OrderCreationTest(String firstName, String lastName, String address, String metroStation, String phone, Number rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[] placeAnOrder() {

        return new Object[][] {
                { "Иван  Иванович", "Иванов ","Славы  ","Речной вокзал", "89521256",4,"2020-06-07","1234",new String[]{"BLACK"}},
                { "Иван  Иванович", "Иванов ","Славы  ","Речной вокзал", "89521256",4,"2020-06-07","1234",new String[]{"BLACK","GREY"}},
                { "Иван  Иванович", "Иванов ","Славы  ","Речной вокзал", "89521256",4,"2020-06-07","1234",new String[]{}},
        };
    }


    @Test
    public void createAnOrder() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(OrderCreationTest.placeAnOrder())
                        .when()
                        .post(ORDERS);

        response.then().statusCode(HTTP_CREATED).body("track",equalTo(response.getBody().as(Track.class).getTrack() ));
        System.out.println(response.getBody().as(Track.class).getTrack());

    }


}
