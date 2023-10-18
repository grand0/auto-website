package ru.kpfu.itis.gr201.ponomarev.cars.model;

import java.util.Objects;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    private String login;
    private String password;

    public User(String firstName, String lastName, String email, String avatarUrl, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.login = login;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String email, String avatarUrl, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getAvatarUrl(), user.getAvatarUrl()) && Objects.equals(getLogin(), user.getLogin()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getAvatarUrl(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName);
        if (lastName != null) {
            sb.append(" ").append(lastName);
        }
        return sb.toString();
    }
}
