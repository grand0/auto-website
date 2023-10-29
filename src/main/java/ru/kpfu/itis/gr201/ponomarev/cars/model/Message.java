package ru.kpfu.itis.gr201.ponomarev.cars.model;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int senderId;
    private int recipientId;
    private int advertisementId;
    private String message;
    private Timestamp sentTs;
    private boolean isRead;

    public Message(int senderId, int recipientId, int advertisementId, String message, Timestamp sentTs, boolean isRead) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.advertisementId = advertisementId;
        this.message = message;
        this.sentTs = sentTs;
        this.isRead = isRead;
    }

    public Message(int id, int senderId, int recipientId, int advertisementId, String message, Timestamp sentTs, boolean isRead) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.advertisementId = advertisementId;
        this.message = message;
        this.sentTs = sentTs;
        this.isRead = isRead;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return message;
    }
}
