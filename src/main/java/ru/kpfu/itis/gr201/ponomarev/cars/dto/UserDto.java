package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import java.util.Objects;

public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    public UserDto(String firstName, String lastName, String email, String avatarUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getFirstName(), userDto.getFirstName()) && Objects.equals(getLastName(), userDto.getLastName()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getAvatarUrl(), userDto.getAvatarUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getAvatarUrl());
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
