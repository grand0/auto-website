package ru.kpfu.itis.gr201.ponomarev.cars.model;

import java.util.Objects;

public class Car {
    private int id;
    private int modelId;
    private Body body;
    private Transmission transmission;
    private Engine engine;
    private Drive drive;
    private float engineVolume;
    private int year;
    private int horsepower;
    private boolean leftWheel;

    public Car(int modelId, Body body, Transmission transmission, Engine engine, Drive drive, float engineVolume, int year, int horsepower, boolean leftWheel) {
        this.modelId = modelId;
        this.body = body;
        this.transmission = transmission;
        this.engine = engine;
        this.drive = drive;
        this.engineVolume = engineVolume;
        this.year = year;
        this.horsepower = horsepower;
        this.leftWheel = leftWheel;
    }

    public Car(int id, int modelId, Body body, Transmission transmission, Engine engine, Drive drive, float engineVolume, int year, int horsepower, boolean leftWheel) {
        this.id = id;
        this.modelId = modelId;
        this.body = body;
        this.transmission = transmission;
        this.engine = engine;
        this.drive = drive;
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

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public float getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(float engineVolume) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return getId() == car.getId() && getModelId() == car.getModelId() && Double.compare(getEngineVolume(), car.getEngineVolume()) == 0 && getYear() == car.getYear() && getHorsepower() == car.getHorsepower() && isLeftWheel() == car.isLeftWheel() && getBody() == car.getBody() && getTransmission() == car.getTransmission() && getEngine() == car.getEngine() && getDrive() == car.getDrive();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModelId(), getBody(), getTransmission(), getEngine(), getDrive(), getEngineVolume(), getYear(), getHorsepower(), isLeftWheel());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", modelId=" + modelId +
                ", body=" + body +
                ", transmission=" + transmission +
                ", engine=" + engine +
                ", drive=" + drive +
                ", engineVolume=" + engineVolume +
                ", year=" + year +
                ", horsepower=" + horsepower +
                ", leftWheel=" + leftWheel +
                '}';
    }
}
