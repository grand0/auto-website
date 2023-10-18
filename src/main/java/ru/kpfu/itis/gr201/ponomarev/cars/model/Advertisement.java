package ru.kpfu.itis.gr201.ponomarev.cars.model;

import java.sql.Timestamp;

public class Advertisement {
    private int id;
    private int carId;
    private String description;
    private int price;
    private int sellerId;
    private Timestamp publicationTs;
    private int mileage;
    private String carColor;
    private int conditionId;
    private int owners;
    private boolean exchangeAllowed;
    private int viewCount;

    public Advertisement(int id, int carId, String description, int price, int sellerId, Timestamp publicationTs, int mileage, String carColor, int conditionId, int owners, boolean exchangeAllowed, int viewCount) {
        this.id = id;
        this.carId = carId;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.publicationTs = publicationTs;
        this.mileage = mileage;
        this.carColor = carColor;
        this.conditionId = conditionId;
        this.owners = owners;
        this.exchangeAllowed = exchangeAllowed;
        this.viewCount = viewCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Timestamp getPublicationTs() {
        return publicationTs;
    }

    public void setPublicationTs(Timestamp publicationTs) {
        this.publicationTs = publicationTs;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public boolean isExchangeAllowed() {
        return exchangeAllowed;
    }

    public void setExchangeAllowed(boolean exchangeAllowed) {
        this.exchangeAllowed = exchangeAllowed;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
