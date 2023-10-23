package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class AdvertisementDto {
    private int id;
    private CarDto car;
    private String description;
    private int price;
    private UserDto seller;
    private Timestamp publicationTs;
    private int mileage;
    private String carColor;
    private Condition condition;
    private int owners;
    private boolean exchangeAllowed;
    private int viewCount;
    private List<String> imagesUrls;

    public AdvertisementDto(int id, CarDto car, String description, int price, UserDto seller, Timestamp publicationTs, int mileage, String carColor, Condition condition, int owners, boolean exchangeAllowed, int viewCount, List<String> imagesUrls) {
        this.id = id;
        this.car = car;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.publicationTs = publicationTs;
        this.mileage = mileage;
        this.carColor = carColor;
        this.condition = condition;
        this.owners = owners;
        this.exchangeAllowed = exchangeAllowed;
        this.viewCount = viewCount;
        this.imagesUrls = imagesUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
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

    public UserDto getSeller() {
        return seller;
    }

    public void setSeller(UserDto seller) {
        this.seller = seller;
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

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
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

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertisementDto that = (AdvertisementDto) o;
        return getId() == that.getId() && getPrice() == that.getPrice() && getMileage() == that.getMileage() && getOwners() == that.getOwners() && isExchangeAllowed() == that.isExchangeAllowed() && getViewCount() == that.getViewCount() && Objects.equals(getCar(), that.getCar()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getSeller(), that.getSeller()) && Objects.equals(getPublicationTs(), that.getPublicationTs()) && Objects.equals(getCarColor(), that.getCarColor()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(getImagesUrls(), that.getImagesUrls());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCar(), getDescription(), getPrice(), getSeller(), getPublicationTs(), getMileage(), getCarColor(), getCondition(), getOwners(), isExchangeAllowed(), getViewCount(), getImagesUrls());
    }

    @Override
    public String toString() {
        return condition + " " + carColor + " " + car;
    }
}
