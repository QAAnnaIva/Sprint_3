import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierLoginTest extends EndPoints{

    @Before
    public void setUp() {

                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(CREATE_COURIER)
                        .when()
                        .post(BASE_URI + COURIER);
    }

    @Test
    public void loginInto(){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(LOGIN_COURIER)
                        .post(BASE_URI + LOGIN);
        response.then().statusCode(HTTP_OK).body("id",equalTo(response.getBody().as(UserId.class).getId()));


    }

    @Test
    public void incorrectLogin(){

       Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(INCORRECT_LOGIN)
                        .post(BASE_URI + LOGIN);

        response.then().statusCode(HTTP_NOT_FOUND).body("message",equalTo("Учетная запись не найдена"));

    }

    @Test
    public void incorrectPassword(){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(INCORRECT_PASSWORD)
                        .post(BASE_URI + LOGIN);

        response.then().statusCode(HTTP_NOT_FOUND).body("message",equalTo("Учетная запись не найдена"));

    }

    @Test
    public void withoutPassword(){

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(EMPTY_PASSWORD)
                        .post(BASE_URI + LOGIN);

        response.then().statusCode(HTTP_BAD_REQUEST).body("message",equalTo("Недостаточно данных для входа"));

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
                .when()
                .delete(BASE_URI + COURIER + userId.getId());
    }
}
