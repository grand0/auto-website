package ru.kpfu.itis.gr201.ponomarev.cars.model;

public enum Drive {
    FRONT(1, "Front"),
    BACK(2, "Back"),
    FULL(3, "Full");

    private final int id;
    private final String drive;

    Drive(int id, String drive) {
        this.id = id;
        this.drive = drive;
    }

    public static Drive getById(int id) {
        for (Drive drive : values()) {
            if (drive.id == id) return drive;
        }
        return null;
    }

    public static Drive getByName(String name) {
        for (Drive drive : values()) {
            if (drive.drive.equals(name)) return drive;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getDrive() {
        return drive;
    }

    @Override
    public String toString() {
        return drive;
    }
}
