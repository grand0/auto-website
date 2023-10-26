package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class MessageDto {
    private UserDto sender;
    private String message;
    private String sentDateTime;

    public MessageDto(UserDto sender, String message, String sentDateTime) {
        this.sender = sender;
        this.message = message;
        this.sentDateTime = sentDateTime;
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(getSender(), that.getSender()) && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getSentDateTime(), that.getSentDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSender(), getMessage(), getSentDateTime());
    }

    @Override
    public String toString() {
        return message;
    }
}
