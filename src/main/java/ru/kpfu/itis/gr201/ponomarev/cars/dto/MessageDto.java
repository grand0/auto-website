package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class MessageDto {
    private UserDto sender;
    private String message;
    private Timestamp sentTs;

    public MessageDto(UserDto sender, String message, Timestamp sentTs) {
        this.sender = sender;
        this.message = message;
        this.sentTs = sentTs;
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

    public Timestamp getSentTs() {
        return sentTs;
    }

    public void setSentTs(Timestamp sentTs) {
        this.sentTs = sentTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(getSender(), that.getSender()) && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getSentTs(), that.getSentTs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSender(), getMessage(), getSentTs());
    }

    @Override
    public String toString() {
        return message;
    }
}
