package ru.praktikum;

public class Login {
    private String login;
    private String password;


    public Login(String login, String password) {
        this.login = login;
        this.password = password;

    }
    public static final Login LOGIN_COURIER = new Login("anna_ivanova","12345678");
    public static final Login INCORRECT_LOGIN = new Login("ann","12345678");
    public static final Login INCORRECT_PASSWORD = new Login("anna_ivanova","1234567");
    public static final Login EMPTY_PASSWORD = new Login("anna_ivanova","");


}
