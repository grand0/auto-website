package ru.kpfu.itis.gr201.ponomarev.cars.model;

public class Car {
    private int id;
    private int makeId;
    private int modelId;
    private int bodyId;
    private int transmissionId;
    private int engineId;
    private int driveId;
    private double engineVolume;
    private int year;
    private int horsepower;
    private boolean leftWheel;

    public Car(int id, int makeId, int modelId, int bodyId, int transmissionId, int engineId, int driveId, double engineVolume, int year, int horsepower, boolean leftWheel) {
        this.id = id;
        this.makeId = makeId;
        this.modelId = modelId;
        this.bodyId = bodyId;
        this.transmissionId = transmissionId;
        this.engineId = engineId;
        this.driveId = driveId;
        this.engineVolume = engineVolume;
        this.year = year;
        this.horsepower = horsepower;
        this.leftWheel = leftWheel;
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

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public int getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(int transmissionId) {
        this.transmissionId = transmissionId;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public int getDriveId() {
        return driveId;
    }

    public void setDriveId(int driveId) {
        this.driveId = driveId;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public boolean isLeftWheel() {
        return leftWheel;
    }

    public void setLeftWheel(boolean leftWheel) {
        this.leftWheel = leftWheel;
    }
}
