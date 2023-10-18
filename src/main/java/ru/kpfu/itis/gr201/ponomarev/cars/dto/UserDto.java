package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import ru.kpfu.itis.gr201.ponomarev.cars.util.CloudinaryUtil;

import java.util.Objects;

public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    public UserDto(int id, String firstName, String lastName, String email, String avatarUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public String getRoundCroppedAvatarUrl() {
        return CloudinaryUtil.getRoundCroppedImageUrl(avatarUrl);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return getId() == userDto.getId() && Objects.equals(getFirstName(), userDto.getFirstName()) && Objects.equals(getLastName(), userDto.getLastName()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getAvatarUrl(), userDto.getAvatarUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getAvatarUrl());
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
