package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Condition {
    private int id;
    private String condition;

    public Condition(int id, String condition) {
        this.id = id;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
