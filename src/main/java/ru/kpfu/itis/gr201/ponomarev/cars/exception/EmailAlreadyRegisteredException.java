package ru.kpfu.itis.gr201.ponomarev.cars.exception;

public class EmailAlreadyRegisteredException extends UserSaveException {
    private final String email;

    public EmailAlreadyRegisteredException() {
        super();
        this.email = null;
    }

    public EmailAlreadyRegisteredException(String email) {
        super("Account with email " + email + " already registered");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
