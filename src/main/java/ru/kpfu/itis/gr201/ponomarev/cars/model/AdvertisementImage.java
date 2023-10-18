package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class AdvertisementImage {
    private int id;
    private int advertisementId;
    private String imageUrl;

    public AdvertisementImage(int id, int advertisementId, String imageUrl) {
        this.id = id;
        this.advertisementId = advertisementId;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
