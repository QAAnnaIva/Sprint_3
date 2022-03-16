import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.UserId;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.CreateCourier.CREATE_COURIER;
import static ru.praktikum.CreateCourier.CREATE_COURIER_WITHOUT_PASSWORD;
import static ru.praktikum.EndPoints.*;
import static ru.praktikum.Login.LOGIN_COURIER;


public class CourierCreationTest  {


    @Test
    public void createNewCourier(){
//запрос на создание курьера
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(CREATE_COURIER)
                        .when()
                        .post(BASE_URI + COURIER);
        response.then().statusCode(HTTP_CREATED).body("ok", equalTo(true));

    }

    @Test
    public void loginInUse(){
//запрос на создание курьера
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(CREATE_COURIER)
                        .when()
                        .post(BASE_URI + COURIER);
        response.then().statusCode(HTTP_CREATED).body("ok", equalTo(true));

        //повторный запрос на создание с теми же параметрами

        Response twice =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(CREATE_COURIER)
                        .when()
                        .post(BASE_URI + COURIER);
        twice.then().statusCode(HTTP_CONFLICT).body("code", equalTo(409)).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));


    }

    @Test
    public void notEnoughDataToCreateAnAccount(){

//запрос на создание курьера, не указан обязательный параметр - пароль
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(CREATE_COURIER_WITHOUT_PASSWORD)
                        .when()
                        .post(BASE_URI + COURIER);
        response.then().statusCode(HTTP_BAD_REQUEST).body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }


    @After
    public void delete (){

        UserId userId =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(LOGIN_COURIER)
                        .when()
                        .post(BASE_URI + LOGIN)
                        .body().as(UserId.class);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(LOGIN_COURIER)
                .when()
                .delete(BASE_URI + COURIER + userId.getId());
    }


}
