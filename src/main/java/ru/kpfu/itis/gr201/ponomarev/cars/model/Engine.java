package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Engine {
    private int id;
    private String engine;

    public Engine(int id, String engine) {
        this.id = id;
        this.engine = engine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
