package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Drive {
    private int id;
    private String drive;

    public Drive(int id, String drive) {
        this.id = id;
        this.drive = drive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }
}
