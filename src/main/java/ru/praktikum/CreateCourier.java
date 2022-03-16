package ru.praktikum;

public class CreateCourier {
    private String login;
    private String password;
    private String firstName;

    public CreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static final CreateCourier CREATE_COURIER = new CreateCourier("anna_ivanova","12345678","Anna");
    public static final CreateCourier CREATE_COURIER_WITHOUT_PASSWORD = new CreateCourier("anna_14",null,"Анна");

}
