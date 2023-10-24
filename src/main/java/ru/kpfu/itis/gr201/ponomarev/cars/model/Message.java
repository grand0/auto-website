package ru.kpfu.itis.gr201.ponomarev.cars.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private int id;
    private int senderId;
    private int recipientId;
    private int advertisementId;
    private String message;
    private Timestamp sentTs;

    public Message(int senderId, int recipientId, int advertisementId, String message, Timestamp sentTs) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.advertisementId = advertisementId;
        this.message = message;
        this.sentTs = sentTs;
    }

    public Message(int id, int senderId, int recipientId, int advertisementId, String message, Timestamp sentTs) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.advertisementId = advertisementId;
        this.message = message;
        this.sentTs = sentTs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
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
        Message message1 = (Message) o;
        return getId() == message1.getId() && getSenderId() == message1.getSenderId() && getRecipientId() == message1.getRecipientId() && getAdvertisementId() == message1.getAdvertisementId() && Objects.equals(getMessage(), message1.getMessage()) && Objects.equals(getSentTs(), message1.getSentTs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSenderId(), getRecipientId(), getAdvertisementId(), getMessage(), getSentTs());
    }

    @Override
    public String toString() {
        return message;
    }
}
