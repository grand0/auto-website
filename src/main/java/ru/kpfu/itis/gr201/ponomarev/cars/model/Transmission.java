package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Transmission {
    private int id;
    private String transmission;

    public Transmission(int id, String transmission) {
        this.id = id;
        this.transmission = transmission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
