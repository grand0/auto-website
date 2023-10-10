package ru.kpfu.itis.gr201.ponomarev.cars.exception;

public class LoginAlreadyTakenException extends UserSaveException {
    private final String login;

    public LoginAlreadyTakenException() {
        super();
        this.login = null;
    }

    public LoginAlreadyTakenException(String login) {
        super("Login \"" + login + "\" already taken");
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
