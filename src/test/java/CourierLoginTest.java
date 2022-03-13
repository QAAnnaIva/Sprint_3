import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
    }
    CreateCourier createCourier = new CreateCourier("anna_ivanova","12345678","Anna");
    Login login = new Login("ann","12345678");
    @Test
    public void loginInto(){
        Login login = new Login("anna_ivanova","12345678");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(login)
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200).body("id",equalTo(response.getBody().as(UserId.class).getId()));


    }

    @Test
    public void incorrectLogin(){

       Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(login)
                        .post("/api/v1/courier/login");

        response.then().assertThat().statusCode(404).body("message",equalTo("Учетная запись не найдена"));

    }

    @Test
    public void incorrectPassword(){

        Login login = new Login("anna_ivanova","1234567");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(login)
                        .post("/api/v1/courier/login");

        response.then().assertThat().statusCode(404).body("message",equalTo("Учетная запись не найдена"));

    }

    @Test
    public void withoutPassword(){

        Login login = new Login("anna_ivanova","");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(login)
                        .post("/api/v1/courier/login");

        response.then().assertThat().statusCode(400).body("message",equalTo("Недостаточно данных для входа"));

    }

    @After
    public void delete (){
        UserId userId =
        given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .body().as(UserId.class);

        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + userId.getId());
    }
}
