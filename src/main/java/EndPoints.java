

public class EndPoints {

    public static final String ORDERS = "api/v1/orders";
    public static final String COURIER = "api/v1/courier/";
    public static final String LOGIN = "api/v1/courier/login" ;
    public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";

    public static final CreateCourier CREATE_COURIER = new CreateCourier("anna_ivanova","12345678","Anna");
    public static final CreateCourier CREATE_COURIER_WITHOUT_PASSWORD = new CreateCourier("anna_14",null,"Анна");
    public static final Login LOGIN_COURIER = new Login("anna_ivanova","12345678");
    public static final Login INCORRECT_LOGIN = new Login("ann","12345678");
    public static final Login INCORRECT_PASSWORD = new Login("anna_ivanova","1234567");
    public static final Login EMPTY_PASSWORD = new Login("anna_ivanova","");

}
