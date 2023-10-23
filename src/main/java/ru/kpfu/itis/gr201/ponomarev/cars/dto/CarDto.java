package ru.kpfu.itis.gr201.ponomarev.cars.dto;

import ru.kpfu.itis.gr201.ponomarev.cars.model.Body;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Drive;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Engine;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Transmission;

import java.util.Objects;

public class CarDto {
    private int id;
    private String make;
    private String model;
    private Body body;
    private Transmission transmission;
    private Engine engine;
    private Drive drive;
    private double engineVolume;
    private int year;
    private int horsepower;
    private boolean leftWheel;

    public CarDto(int id, String make, String model, Body body, Transmission transmission, Engine engine, Drive drive, double engineVolume, int year, int horsepower, boolean leftWheel) {
        this.id = id;
        this.make = make;
        this.model = model;
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return getId() == carDto.getId() && Double.compare(getEngineVolume(), carDto.getEngineVolume()) == 0 && getYear() == carDto.getYear() && getHorsepower() == carDto.getHorsepower() && isLeftWheel() == carDto.isLeftWheel() && Objects.equals(getMake(), carDto.getMake()) && Objects.equals(getModel(), carDto.getModel()) && getBody() == carDto.getBody() && getTransmission() == carDto.getTransmission() && getEngine() == carDto.getEngine() && getDrive() == carDto.getDrive();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMake(), getModel(), getBody(), getTransmission(), getEngine(), getDrive(), getEngineVolume(), getYear(), getHorsepower(), isLeftWheel());
    }

    @Override
    public String toString() {
        return make + " " + model + " (" + year + ")";
    }
}
