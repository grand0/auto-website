package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Model {
    private int id;
    private int makeId;
    private String model;

    public Model(int id, int makeId, String model) {
        this.id = id;
        this.makeId = makeId;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
