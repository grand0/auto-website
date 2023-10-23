package ru.kpfu.itis.gr201.ponomarev.cars.model;

public enum Condition {
    EXCELLENT(1, "Excellent"),
    VERY_GOOD(2, "Very good"),
    GOOD(3, "Good"),
    POOR(4, "Poor"),
    NOT_RUNNING(5, "Not running");

    private final int id;
    private final String condition;

    Condition(int id, String condition) {
        this.id = id;
        this.condition = condition;
    }

    public static Condition getById(int id) {
        for (Condition condition : values()) {
            if (condition.id == id) return condition;
        }
        return null;
    }

    public static Condition getByName(String name) {
        for (Condition condition : values()) {
            if (condition.condition.equals(name)) return condition;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return condition;
    }
}
