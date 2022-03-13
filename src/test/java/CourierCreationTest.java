import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreationTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";


    }
    CreateCourier createCourier = new CreateCourier("anna_ivanova","12345678","Anna");
    Login login = new Login("anna_ivanova","12345678");


    @Test
    public void createNewCourier(){
//запрос на создание курьера
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
        response.then().assertThat().statusCode(201).body("ok", equalTo(true));

    }

    @Test
    public void loginInUse(){
//запрос на создание курьера
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
        response.then().assertThat().statusCode(201).body("ok", equalTo(true));

        //повторный запрос на создание с теми же параметрами

        Response twice =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
        twice.then().assertThat().statusCode(409).body("code", equalTo(409)).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));


    }

    @Test
    public void notEnoughDataToCreateAnAccount(){
        CreateCourier createCourier = new CreateCourier("anna_14",null,"Анна");
//запрос на создание курьера, не указан обязательный параметр - пароль
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("api/v1/courier");
        response.then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));

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
                .body(login)
                .when()
                .delete("/api/v1/courier/" + userId.getId());
    }


}
