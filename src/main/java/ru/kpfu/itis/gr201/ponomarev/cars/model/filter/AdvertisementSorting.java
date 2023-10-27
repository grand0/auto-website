package ru.kpfu.itis.gr201.ponomarev.cars.model.filter;

public enum AdvertisementSorting {
    CAR_NAME(1, "Car name"),
    MILEAGE(2, "Mileage"),
    PRICE(3, "Price"),
    PUBLICATION_TIME(4, "Publication time"),
    VIEWS(5, "Views");

    private boolean isDesc = true;
    private final int id;
    private final String name;

    AdvertisementSorting(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AdvertisementSorting getById(int id) {
        for (AdvertisementSorting s : values()) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    public static AdvertisementSorting getByName(String name) {
        for (AdvertisementSorting s : values()) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public boolean isDesc() {
        return isDesc;
    }

    public void setDesc(boolean desc) {
        isDesc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
