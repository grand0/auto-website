package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Make {
    private int id;
    private String make;

    public Make(String make) {
        this.make = make;
    }

    public Make(int id, String make) {
        this.id = id;
        this.make = make;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
